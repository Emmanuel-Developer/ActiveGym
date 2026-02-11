package com.linkersconsulting.activegym.features.auth.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import com.linkersconsulting.activegym.databinding.FragmentRegisterBinding
import com.linkersconsulting.activegym.utils.toast

class RegisterFragment : Fragment() {
    private val binding by lazy {
        FragmentRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root  // Se inicializa aquí automáticamente
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() = binding.apply {

        btnRegister.setOnClickListener {
            toast("Cliente guardado exitosamente!")
        }

    }
}


