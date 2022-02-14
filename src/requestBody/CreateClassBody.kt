package com.example.requestBody

data class CreateClassBody(
    var name: String,
    var schoolName: String ,
    val stage: Int?,
)