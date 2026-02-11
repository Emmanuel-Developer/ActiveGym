package com.linkersconsulting.activegym.features.auth.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import com.linkersconsulting.activegym.databinding.FragmentRegisterBinding
import com.linkersconsulting.activegym.utils.toast

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.btnRegister.setOnClickListener {
            toast("Cliente guardado exitosamente!")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


