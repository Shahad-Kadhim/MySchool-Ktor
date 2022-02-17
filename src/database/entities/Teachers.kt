package com.example.database.entities

import org.jetbrains.exposed.sql.Table


object Teachers: Table("teachers") {

    val id = varchar("id",50).primaryKey() references Users.id
    val teachingSpecialization = varchar("teachingSpecialization",30)

}
