package com.example.requestBody

data class MangerRegisterBody (
    var name: String,
    var password: String,
    val phone: Long,
    var firebaseToken: String
)