package com.example.database.entities

import org.jetbrains.exposed.sql.Table

object Posts: Table("post") {
    val id = varchar("id",50).primaryKey()
    val title = varchar("title",20)
    val content = varchar("content",100)
    val payload = text("url").nullable()
    val datePosted = long("datePosted")
    val classId = varchar("classId",50) references Classes.id
    val authorId = varchar("authorId",50) references Teachers.id
    val type= varchar("type",30)
}