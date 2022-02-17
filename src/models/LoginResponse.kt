package com.example.models

import com.example.authentication.Role

data class TokenResponse(
    val role: Role,
    val token: String
)
