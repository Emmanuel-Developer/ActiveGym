package com.linkersconsulting.activegym.features.auth.presentation.ui

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue
import com.linkersconsulting.activegym.R
import com.linkersconsulting.activegym.databinding.FragmentRegisterBinding
import com.linkersconsulting.activegym.utils.toast

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        setupClickListeners()
        return binding.root
    }

    private fun setupClickListeners() {
        binding.btnArrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnRegister.setOnClickListener {
            val gymName = binding.etGymName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (validateInputs(gymName, email, password, confirmPassword)) {
                registerGymOwner(gymName, email, password)
            }
        }
    }

    private fun validateInputs(
        gymName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        var valid = true

        if (gymName.isEmpty()) {
            binding.etGymName.error = "El nombre del gym es obligatorio"
            valid = false
        } else {
            binding.etGymName.error = null
        }

        if (email.isEmpty()) {
            binding.etEmail.error = "El email es obligatorio"
            valid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "El email no es válido"
            valid = false
        } else {
            binding.etEmail.error = null
        }

        if (password.length < 6) {
            binding.etPassword.error = "La contraseña debe tener al menos 6 caracteres"
            valid = false
        } else {
            binding.etPassword.error = null
        }

        if (password != confirmPassword) {
            binding.etConfirmPassword.error = "Las contraseñas no coinciden"
            valid = false
        } else {
            binding.etConfirmPassword.error = null
        }

        return valid
    }

    private fun registerGymOwner(gymName: String, email: String, password: String) {
        binding.btnRegister.isEnabled = false

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: ""
                    saveGymToFirestore(userId, gymName, email)
                } else {
                    binding.btnRegister.isEnabled = true
                    toast("Error: ${task.exception?.message}")
                }
            }
    }

    private fun saveGymToFirestore(userId: String, gymName: String, email: String) {
        val gymData = hashMapOf(
            "gymName" to gymName,
            "ownerEmail" to email,
            "ownerId" to userId,
            "createdAt" to FieldValue.serverTimestamp(),
            "isActive" to true
        )

        firestore.collection("gyms")
            .document(userId)
            .set(gymData)
            .addOnSuccessListener {
                binding.btnRegister.isEnabled = true
                toast("¡Gym registrado exitosamente!")
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
            .addOnFailureListener { e ->
                binding.btnRegister.isEnabled = true
                toast("Error al guardar gym: ${e.message}")
                Log.e("RegisterFragment", "Error saving gym to Firestore", e)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
