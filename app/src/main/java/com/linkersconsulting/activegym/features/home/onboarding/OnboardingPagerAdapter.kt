package com.linkersconsulting.activegym.features.auth.presentation.ui.onboarding

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.linkersconsulting.activegym.R
import com.linkersconsulting.activegym.databinding.ItemOnboardingBinding
import com.linkersconsulting.activegym.features.init.data.OnboardingItem

class OnboardingPagerAdapter(
    private val items: List<OnboardingItem>,
    private val dotsLayout: LinearLayout,
    private val button: Button
) : RecyclerView.Adapter<OnboardingPagerAdapter.ViewHolder>() {

    private var currentPosition = 0
    private val dotViews = mutableListOf<View>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOnboardingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        updateDots(position)
        updateButton(position == items.size - 1)
    }

    override fun getItemCount() = items.size

    fun updatePosition(position: Int) {
        val oldPosition = currentPosition
        currentPosition = position

        // Solo actualizar dot activo ANTES de cambiar posición
        if (oldPosition < dotViews.size) {
            updateSingleDot(oldPosition, false)
        }
        if (position < dotViews.size) {
            updateSingleDot(position, true)
        }

        updateButton(position == items.size - 1)
    }

    private fun updateDots(initialPosition: Int) {
        // Crear dots SOLO la primera vez
        if (dotViews.isEmpty()) {
            createDots(initialPosition)
        }
    }

    private fun createDots(activePosition: Int) {
        dotsLayout.removeAllViews()
        dotViews.clear()

        items.forEachIndexed { index, _ ->
            val dot = View(dotsLayout.context).apply {
                val params = LinearLayout.LayoutParams(10.dp, 10.dp).apply {
                    setMargins(if (index > 0) 8.dp else 0, 0, 0, 0)
                }
                layoutParams = params
                background = ContextCompat.getDrawable(
                    dotsLayout.context,
                    if (index == activePosition) R.drawable.dot_active else R.drawable.dot_inactive
                )
            }
            dotsLayout.addView(dot)
            dotViews.add(dot)
        }
    }

    private fun updateSingleDot(index: Int, isActive: Boolean) {
        if (index < dotViews.size) {
            dotViews[index].background = ContextCompat.getDrawable(
                dotsLayout.context,
                if (isActive) R.drawable.dot_active else R.drawable.dot_inactive
            )
        }
    }

    private fun updateButton(isLast: Boolean) {
        button.text = if (isLast) "¡Comenzar!" else "¡Entendido!"
    }

    class ViewHolder(private val binding: ItemOnboardingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OnboardingItem) {
            binding.apply {
                bgImage.setImageResource(item.bgImage)
                title.text = item.title
                description.text = item.description
            }
        }
    }

    private val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()
}
