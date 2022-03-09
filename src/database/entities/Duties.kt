package com.example.database.entities

import org.jetbrains.exposed.sql.Table


object Duties : Table("duty") {
    val id = varchar("id",50).primaryKey() references Posts.id
    val degree = integer("degree").default(10)
}
