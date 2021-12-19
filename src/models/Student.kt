package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Student (
    val id: Long,
    var name: String,
    var password: String,
    var age: Int,
    var note: String,
    var phone: Int ,
    var stage: Int
)