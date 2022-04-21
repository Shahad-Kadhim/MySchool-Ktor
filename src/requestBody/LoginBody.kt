package com.example.requestBody

data class LoginBody(
    val name: String,
    val password: String,
    val firebaseToken: String
)
