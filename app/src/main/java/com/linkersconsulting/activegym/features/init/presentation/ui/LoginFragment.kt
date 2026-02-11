package com.linkersconsulting.activegym.features.init.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.linkersconsulting.activegym.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.button.MaterialButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.linkersconsulting.activegym.databinding.FragmentLoginBinding
import com.linkersconsulting.activegym.databinding.FragmentRegisterBinding
import com.linkersconsulting.activegym.utils.toast


class LoginFragment : Fragment() {
    private val binding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    private fun initView() = binding.apply {

        btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Limpia errores previos
            tilEmail.error = null
            tilPassword.error = null

            when {
                email.isEmpty() -> {
                    tilEmail.error = "El correo es obligatorio"
                }

                password.isEmpty() -> {
                    tilPassword.error = "La contraseña es obligatoria"
                }

                else -> {
                    // Aquí irá el login real
                    Toast.makeText(
                        requireContext(),
                        "Email: $email\nPassword: $password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }
}