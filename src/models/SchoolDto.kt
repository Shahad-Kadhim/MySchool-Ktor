package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class SchoolDto(
    val id: String,
    var name: String ,
    var mangerName: String ,
)
