package com.linkersconsulting.activegym.features.auth.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.linkersconsulting.activegym.R
import com.linkersconsulting.activegym.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
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
                    loginUser(email, password)
                }
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        binding.btnLogin.isEnabled = false

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                binding.btnLogin.isEnabled = true

                if (task.isSuccessful) {
                    toast("¡Bienvenido!")

                    // 🔥 VERIFICA ONBOARDING POR USUARIO
                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        val prefs = requireContext()
                            .getSharedPreferences("onboarding_per_user", Context.MODE_PRIVATE)
                        val userId = currentUser.uid

                        if (prefs.getBoolean(userId, false)) {
                            // Usuario ya vio onboarding → HOME
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        } else {
                            // PRIMERA VEZ esta cuenta → ONBOARDING
                            findNavController().navigate(R.id.action_loginFragment_to_onboardingFragment)
                        }
                    }
                } else {
                    toast("Error: ${task.exception?.message}")
                }
            }
    }

    private fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
