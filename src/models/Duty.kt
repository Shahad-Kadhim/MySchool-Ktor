package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Duty(
    val id: Long,
    val state: String, // DONE , TO_DO or Missing
    val studentID: Long,
    var title: String,
    var content: String,
)
