package com.example.database.entities

import org.jetbrains.exposed.sql.Table


object TeachersSchool: Table("teacher-school") {
    val teacherId =( varchar("teacherId",50) references Teachers.id).primaryKey()
    val schoolId = (varchar("schoolId",50) references Schools.id).primaryKey()
    val dateJoined = long("dateJoined")


}