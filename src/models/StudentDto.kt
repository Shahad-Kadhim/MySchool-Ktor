package com.example.models

import kotlinx.serialization.Serializable

data class StudentDto (
    val id: String,
    var name: String,
    var age: Int,
    var note: String,
    var phone: Long ,
    var stage: Int,
    var firebaseToken: String
)