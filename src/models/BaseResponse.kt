package com.example.models

data class BaseResponse<T>(
    val statusCode: Int,
    val data: T
)
