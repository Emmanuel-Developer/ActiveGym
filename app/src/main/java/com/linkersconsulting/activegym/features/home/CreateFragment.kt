package com.linkersconsulting.activegym.features.home

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.linkersconsulting.activegym.databinding.FragmentCreateBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.linkersconsulting.activegym.R
import java.text.SimpleDateFormat
import java.util.*

class CreateFragment : Fragment(R.layout.fragment_create) {

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: FirebaseFirestore
    private var joinDateMillis: Long = 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCreateBinding.bind(view)
        db = FirebaseFirestore.getInstance()

        setupDatePicker()

        setupDropdowns()

        binding.btnSaveUser.setOnClickListener {

            val userId = binding.etUserId.text.toString().trim()
            val name = binding.etName.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()
            val address = binding.etAddress.text.toString().trim()
            val postalCode = binding.etPostalCode.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val gender = binding.actvGender.text.toString().trim()
            val membership = binding.actvMembership.text.toString().trim()

            if (
                userId.isEmpty() || name.isEmpty() || phone.isEmpty() ||
                address.isEmpty() || postalCode.isEmpty() ||
                email.isEmpty() || gender.isEmpty() ||
                membership.isEmpty() || joinDateMillis == 0L
            ) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            saveUser(
                userId, name, phone, address,
                postalCode, email, gender, membership
            )
        }
    }

    private fun setupDropdowns() {

        val genders = listOf("Masculino", "Femenino", "Otro")
        binding.actvGender.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, genders)
        )

        val memberships = listOf("Diaria", "Semanal", "Mensual", "Anual")
        binding.actvMembership.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, memberships)
        )
    }

    private fun setupDatePicker() {

        val constraints = CalendarConstraints.Builder()
            .setEnd(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Selecciona fecha de ingreso")
            .setCalendarConstraints(constraints)
            .build()

        binding.etJoinDate.setOnClickListener {
            datePicker.show(parentFragmentManager, "DATE_PICKER")
        }

        datePicker.addOnPositiveButtonClickListener { selection ->
            joinDateMillis = selection

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            binding.etJoinDate.setText(sdf.format(Date(selection)))
        }
    }

    private fun saveUser(
        userId: String,
        name: String,
        phone: String,
        address: String,
        postalCode: String,
        email: String,
        gender: String,
        membership: String
    ) {

        val calendar = Calendar.getInstance()

        when (membership) {
            "Diaria" -> calendar.add(Calendar.DAY_OF_YEAR, 1)
            "Semanal" -> calendar.add(Calendar.DAY_OF_YEAR, 7)
            "Mensual" -> calendar.add(Calendar.MONTH, 1)
            "Anual" -> calendar.add(Calendar.YEAR, 1)
        }

        val membershipEndDate = calendar.timeInMillis

        val user = hashMapOf(
            "userId" to userId,
            "name" to name,
            "phone" to phone,
            "address" to address,
            "postalCode" to postalCode,
            "joinDate" to joinDateMillis,
            "email" to email,
            "gender" to gender,
            "membershipType" to membership,
            "membershipEndDate" to membershipEndDate,
            "createdAt" to FieldValue.serverTimestamp()
        )

        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Membresía creada 🔥", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error al guardar", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
