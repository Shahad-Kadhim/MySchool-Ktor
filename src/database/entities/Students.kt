package com.example.database.entities

import org.jetbrains.exposed.sql.Table


object Students: Table("student") {

    val id = varchar("id",50).primaryKey()
    val name = varchar("name",30)
    val password = varchar("password",30)
    val age = integer("age")
    val note = text("note")
    val phone = integer("phone")
    val stage = integer("stage")

}
