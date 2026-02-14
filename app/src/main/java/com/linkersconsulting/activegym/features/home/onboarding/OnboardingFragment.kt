package com.linkersconsulting.activegym.features.auth.presentation.ui.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.linkersconsulting.activegym.R
import com.linkersconsulting.activegym.databinding.FragmentOnboardingBinding
import com.linkersconsulting.activegym.features.init.data.OnboardingItem

class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: OnboardingPagerAdapter
    private lateinit var prefs: android.content.SharedPreferences

    private val onboardingItems = listOf(
        OnboardingItem(
            R.drawable.ic_login,
            "¡Bienvenido!",
            "¡Administra tu Gym!. Controla miembros, pagos y entrenamientos desde una sola app 💪"
        ),
        OnboardingItem(
            R.drawable.ic_login,
            "Gestiona Clientes",
            "Registra asistencias, crea planes y sigue el progreso de tus alumnos 📋"
        ),
        OnboardingItem(
            R.drawable.ic_register_logo,
            "¡Optimiza tu Negocio!",
            "Reportes, ingresos y notificaciones automáticas para crecer 📈"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = requireContext().getSharedPreferences("onboarding", Context.MODE_PRIVATE)

        if (prefs.getBoolean("seen", false)) {
            navigateToMain()
            return
        }

        setupViewPager()
    }

    private fun setupViewPager() {
        adapter = OnboardingPagerAdapter(onboardingItems, binding.dots, binding.button)
        binding.viewPager.adapter = adapter

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                adapter.updatePosition(position)
            }
        })

        binding.button.setOnClickListener {
            val currentPosition = binding.viewPager.currentItem
            if (currentPosition < onboardingItems.size - 1) {
                binding.viewPager.currentItem = currentPosition + 1
            } else {
                completeOnboarding()
            }
        }
    }

    private fun completeOnboarding() {
        prefs.edit().putBoolean("seen", true).apply()
        navigateToMain()
    }

    private fun navigateToMain() {
        findNavController().navigate(R.id.action_onboardingFragment_to_homeFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
