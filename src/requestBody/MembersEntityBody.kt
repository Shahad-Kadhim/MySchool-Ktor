package com.example.requestBody

import kotlinx.serialization.Serializable
import java.util.*

data class MembersEntityBody(
    val membersId: List<String>,
    val entityId: String,
)