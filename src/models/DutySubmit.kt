package com.example.models

data class DutySubmit(
    val studentId: String,
    val dutyId: String,
    val submitDate: Long?,
    var solutionLink: String

)