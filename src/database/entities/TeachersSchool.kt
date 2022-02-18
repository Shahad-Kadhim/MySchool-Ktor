package com.example.database.entities

import org.jetbrains.exposed.sql.Table


object TeachersSchool: Table("teacher-school") {
    val teacherId =( varchar("teacherId",50).primaryKey() references Teachers.id)
    val schoolId = (varchar("schoolId",50).primaryKey() references Schools.id)
    val dateJoined = long("dateJoined")


}