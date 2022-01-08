package com.example.database.entities


import org.jetbrains.exposed.sql.Table


object Mangers : Table("manger") {

    val id = varchar("id",50).primaryKey()
    val name = varchar("name",30)

}
