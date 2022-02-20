package com.example.database.entities

import org.jetbrains.exposed.sql.Table


object Teachers: Table("teachers") {

    val id = varchar("id",100).primaryKey() references Users.id
    val teachingSpecialization = varchar("teachingSpecialization",30)

}
