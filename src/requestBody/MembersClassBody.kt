package com.example.requestBody

import kotlinx.serialization.Serializable
import java.util.*

data class MembersClassBody(
    val studentsId: List<String>,
    val classId: String,
)