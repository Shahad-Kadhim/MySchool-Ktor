package com.example.database.entities

import org.jetbrains.exposed.sql.Table


object StudentsSchool: Table("student-school") {
    val studentId =( varchar("studentId",100).primaryKey() references Students.id)
    val schoolId = (varchar("schoolId",50).primaryKey() references Schools.id)
    val dateJoined = long("dateJoined")

}