package com.example.database.entities

import org.jetbrains.exposed.sql.Table

object Users: Table("users") {
    val id = varchar("id",100).primaryKey()
    val name =text("name")
    val password =text("password")
    val phone = long("phone")
    val role = varchar("role",10) references Roles.role

    init {
        index(true,name)
    }

}