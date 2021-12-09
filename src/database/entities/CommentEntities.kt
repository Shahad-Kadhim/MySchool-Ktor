package com.example.database.entities

import org.ktorm.entity.Entity
import org.ktorm.schema.*

object DBCommentTable: Table<DBCommentEntity>("Comment") {

    val id = varchar("id").primaryKey().bindTo { it.id }
    val postId = varchar("postId").bindTo { it.postId }
    val authorId = varchar("authorId").bindTo { it.authorId }
    val content = varchar("content").bindTo { it.content }
    val dateCommented = varchar("dateCommented").bindTo { it.dateCommented }

}

interface DBCommentEntity: Entity<DBCommentEntity> {

    companion object : Entity.Factory<DBCommentEntity>()

    val id: String
    val postId: String
    val authorId: String
    var content: String
    val dateCommented: String
}