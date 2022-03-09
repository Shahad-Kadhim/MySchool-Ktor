package com.example.database.entities

import org.jetbrains.exposed.sql.Table

object DutyStudent: Table("duty_student") {
    val studentId =varchar("student_id",50).primaryKey() references Students.id
    val dutyId =varchar("duty_id",50).primaryKey() references Duties.id
    val solutionLink = text("url")
    init {
        index(true, studentId, dutyId)
    }
}