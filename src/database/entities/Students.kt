package com.example.database.entities

import org.jetbrains.exposed.sql.Table


object Students: Table("students") {

    val id = varchar("id",50).primaryKey() references Users.id
    val age = integer("age")
    val note = text("note")
    val stage = integer("stage")

}
