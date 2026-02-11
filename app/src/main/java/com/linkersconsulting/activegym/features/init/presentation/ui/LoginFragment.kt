package com.linkersconsulting.activegym.features.init.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.linkersconsulting.activegym.R
import com.linkersconsulting.activegym.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        binding.btnLogin.setOnClickListener {

            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            when {
                email.isEmpty() -> {
                    binding.etEmail.error = "El correo es obligatorio"
                }
                password.isEmpty() -> {
                    binding.etPassword.error = "La contraseña es obligatoria"
                }
                else -> {
                    Toast.makeText(
                        requireContext(),
                        "Email: $email\nPassword: $password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_registerFragment
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
