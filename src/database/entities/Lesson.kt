package com.example.database.entities

import org.jetbrains.exposed.sql.Table

object Lesson: Table("lesson") {
    val id = varchar("id",50).primaryKey() references Posts.id
    val lastDateUpdated = long("lastDateUpdated").nullable()
}