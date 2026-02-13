package com.linkersconsulting.activegym.features.init.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.linkersconsulting.activegym.R
import com.linkersconsulting.activegym.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.linkersconsulting.activegym.utils.toast

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        // 🔥 Inicializar Firebase
        auth = FirebaseAuth.getInstance()

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
                    loginUser(email, password)
                }
            }
        }

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_registerFragment
            )
        }
    }

    private fun loginUser(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                  toast("Bienvenido")

                    findNavController().navigate(
                        R.id.action_loginFragment_to_onboardingFragment
                    )

                } else {

                    Toast.makeText(
                        requireContext(),
                        "Usuario no existe o contraseña incorrecta",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
