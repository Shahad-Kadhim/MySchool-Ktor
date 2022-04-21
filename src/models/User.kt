package com.example.models

import com.example.authentication.Role


data class User(
    val id: String,
    val name: String,
    val password: String,
    val phone: Long,
    val role: Role,
    var firebaseToken: String
)
