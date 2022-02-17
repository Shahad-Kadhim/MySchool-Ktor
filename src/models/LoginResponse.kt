package com.example.models

import com.example.authentication.Role

data class AuthenticationResponse(
    val role: Role,
    val token: String
)
