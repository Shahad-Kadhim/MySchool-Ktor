package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Teacher(
    val id: String ,
    var name: String,
    var password: String,
    var teachingSpecialization: String,
    var phone: Long,
    var firebaseToken: String
)
