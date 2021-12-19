package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class School(
    val id: Long,
    var name: String ,
    var mangerId: Long ,
)
