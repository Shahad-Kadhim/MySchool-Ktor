package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Manger(
    val id: String ,
    var name :String ,
    var password: String,
    val phone: Long
)
