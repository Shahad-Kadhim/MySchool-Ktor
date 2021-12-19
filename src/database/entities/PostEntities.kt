package com.example.database.entities

import org.ktorm.entity.Entity
import org.ktorm.schema.*

object DBPostTable: Table<DBPostEntity>("post") {

    val id = long("id").primaryKey().bindTo { it.id }
    val title = varchar("title").bindTo { it.title }
    val content = varchar("content").bindTo { it.content }
    val datePosted = varchar("datePosted").bindTo { it.datePosted }
    val lastDateUpdated = varchar("lastDateUpdated").bindTo { it.lastDateUpdated }
    val authorId = long("authorId").references(DBTeacherTable){ it.authorId }
}

interface DBPostEntity: Entity<DBPostEntity> {

    companion object : Entity.Factory<DBPostEntity>()

    val id: Long
    var name: String
    var title: String
    var content: String
    var authorId: DBTeacherEntity
    val datePosted :String
    var lastDateUpdated :String
}