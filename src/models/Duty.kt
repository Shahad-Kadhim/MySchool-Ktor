package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Duty(
    val id: String,
    val state: String, // DONE , TO_DO or Missing
    val studentID: String,
    var title: String,
    var content: String,
)
