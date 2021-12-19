package com.example.database.entities

import org.ktorm.entity.Entity
import org.ktorm.schema.*

object DBCommentTable: Table<DBCommentEntity>("Comment") {

    val id = long("id").primaryKey().bindTo { it.id }
    val postId = long("postId").references(DBPostTable) { it.postId }
    val authorId = long("authorId").references(DBTeacherTable){ it.authorId }
    val content = varchar("content").bindTo { it.content }
    val dateCommented = varchar("dateCommented").bindTo { it.dateCommented }

}

interface DBCommentEntity: Entity<DBCommentEntity> {

    companion object : Entity.Factory<DBCommentEntity>()

    val id: Long
    val postId: DBPostEntity
    val authorId: DBTeacherEntity
    var content: String
    val dateCommented: String
}