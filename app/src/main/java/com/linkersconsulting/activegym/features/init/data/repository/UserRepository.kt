package com.linkersconsulting.activegym.features.init.data.repository
import com.google.firebase.firestore.FirebaseFirestore
import com.linkersconsulting.activegym.features.init.data.model.GymMembersDataClass

class UserRepository {

    private val db = FirebaseFirestore.getInstance()

    fun saveUser(user: GymMembersDataClass, onResult: (Boolean) -> Unit) {

        db.collection("users")
            .add(user)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }
}