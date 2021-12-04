package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class School(
    val id: String,
    var name :String ,
    var mangerId : String ,
)
