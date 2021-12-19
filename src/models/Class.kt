package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Class (
    val id: Long,
    var name:String,
    var teacherId: Long ,
    var schoolId: Long ,
    val stage :Int?,
)

