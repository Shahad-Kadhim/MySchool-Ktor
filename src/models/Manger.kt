package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Manger(
    val id: Long ,
    var name :String ,
    var schoolId: Long
)
