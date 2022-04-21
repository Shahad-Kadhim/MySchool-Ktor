package com.example.database.entities

import org.jetbrains.exposed.sql.Table

object Notifications: Table("notification") {
    val id = varchar("id",50).primaryKey()
    val title = varchar("title", 50)
    val content = text("content")
    val date = long("date")
}