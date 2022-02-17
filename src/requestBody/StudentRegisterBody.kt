package com.example.requestBody

data class StudentRegisterBody(
    val name: String,
    val password: String,
    var age: Int,
    var note: String,
    var phone: Long ,
    var stage: Int
)
