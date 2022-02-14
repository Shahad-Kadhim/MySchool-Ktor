package com.example.database.entities

import org.jetbrains.exposed.sql.Table


object Teachers: Table("teacher") {

    val id = varchar("id",50).primaryKey()
    val name = varchar("name",30).uniqueIndex()
    val password = varchar("password",30)
    val teachingSpecialization = varchar("teachingSpecialization",30)
    val phone = integer("phone")

}
