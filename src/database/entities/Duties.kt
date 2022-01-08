package com.example.database.entities

import org.jetbrains.exposed.sql.Table


object Duties : Table("duty") {

    val id = varchar("id",50).primaryKey()
    val studentId =( varchar("name",50) references Students.id)
    val title = varchar("password",30)
    val content = varchar("content",50)
}
