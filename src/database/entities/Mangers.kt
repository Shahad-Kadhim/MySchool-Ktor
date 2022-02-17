package com.example.database.entities


import org.jetbrains.exposed.sql.Table


object Mangers : Table("mangers") {

    val id = varchar("id",50).primaryKey() references Users.id

}
