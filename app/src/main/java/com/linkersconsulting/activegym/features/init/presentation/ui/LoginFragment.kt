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


class LoginFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etEmail = view.findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword = view.findViewById<TextInputEditText>(R.id.etPassword)
        val tilEmail = view.findViewById<TextInputLayout>(R.id.tilEmail)
        val tilPassword = view.findViewById<TextInputLayout>(R.id.tilPassword)
        val btnLogin = view.findViewById<MaterialButton>(R.id.btnLogin)

        btnLogin.setOnClickListener {


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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
}