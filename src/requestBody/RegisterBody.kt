package com.example.requestBody

data class RegisterBody(
    val name: String,
    val password: String,
    var age: Int,
    var note: String,
    var phone: Int ,
    var stage: Int
)
