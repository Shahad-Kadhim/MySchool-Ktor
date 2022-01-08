package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Teacher(
    var name: String,
    val id: String ,
    var password: String,
    var teachingSpecialization: String,
    var phone: Int,
)
