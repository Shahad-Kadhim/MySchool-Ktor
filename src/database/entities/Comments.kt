package com.example.database.entities

import org.jetbrains.exposed.sql.Table


object Comments: Table("comment") {

    val id = varchar("id",50).primaryKey()
    val postId = (varchar("postId",50) references Posts.id)
    val authorId = (varchar("authorId",50) references Teachers.id)
    val content = varchar("content",100)
    val dateCommented = long("dateCommented")
}
