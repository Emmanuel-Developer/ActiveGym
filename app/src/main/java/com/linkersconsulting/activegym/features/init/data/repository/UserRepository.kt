package com.linkersconsulting.activegym.features.init.data.repository
import com.google.firebase.firestore.FirebaseFirestore
import com.linkersconsulting.activegym.features.init.data.model.UserDataClass

class UserRepository {

    private val db = FirebaseFirestore.getInstance()

    fun saveUser(user: UserDataClass, onResult: (Boolean) -> Unit) {

        db.collection("users")
            .add(user)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }
}