package com.linkersconsulting.activegym.features.init.data
import com.google.firebase.Timestamp
data class Gym(
    val gymName: String = "",
    val email: String = "",
    val uid: String = "",
    val createdAt: Long = 0L
)
