package com.example.database.entities

import org.jetbrains.exposed.sql.Table


object MemberClasss: Table("memberClass") {

    val studentId =( varchar("studentId",50) references Students.id)
    val classId = (varchar("classId",50) references Classes.id)
    val dateJoined = long("dateJoined")

}
