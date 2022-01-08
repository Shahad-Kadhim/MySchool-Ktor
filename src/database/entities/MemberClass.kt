package com.example.database.entities

import org.jetbrains.exposed.sql.Table


object MemberClass: Table("member-class") {

    val studentId =( varchar("studentId",50) references Students.id)
    val classId = (varchar("classId",50) references Classes.id)
    val dateJoined = datetime("dateJoined")

}
