package com.example.database.entities

import org.jetbrains.exposed.sql.Table


object Posts: Table("post") {

    val id = varchar("id",50).primaryKey()
    val title = varchar("title",20)
    val content = varchar("content",100)
    val datePosted = datetime("datePosted")
    val lastDateUpdated = datetime("lastDateUpdated")
    val authorId = (varchar("authorId",50) references Teachers.id)
}
