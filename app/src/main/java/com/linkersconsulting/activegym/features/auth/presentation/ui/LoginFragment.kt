package com.linkersconsulting.activegym.features.auth.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.linkersconsulting.activegym.R
import com.linkersconsulting.activegym.databinding.FragmentLoginBinding
import android.widget.Toast
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Limpia errores previos
            binding.tilEmail.error = null
            binding.tilPassword.error = null

            when {
                email.isEmpty() -> {
                    binding.tilEmail.error = "El correo es obligatorio"
                }
                password.isEmpty() -> {
                    binding.tilPassword.error = "La contraseña es obligatoria"
                }
                else -> {
                    // Aquí irá el login real
                    findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}