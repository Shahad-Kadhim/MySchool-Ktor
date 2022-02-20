package com.example.database.entities

import org.jetbrains.exposed.sql.Table


object StudentsSchool: Table("student-school") {
    val studentId =( varchar("studentId",100) references Students.id).primaryKey()
    val schoolId = (varchar("schoolId",50) references Schools.id).primaryKey()
    val dateJoined = long("dateJoined")
}