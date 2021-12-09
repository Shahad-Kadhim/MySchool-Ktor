package com.example.database.entities

import org.ktorm.entity.Entity
import org.ktorm.schema.*

object DBPostTable: Table<DBPostEntity>("Post") {

    val id = varchar("id").primaryKey().bindTo { it.id }
    val title = varchar("title").bindTo { it.title }
    val content = varchar("content").bindTo { it.content }
    val datePosted = varchar("datePosted").bindTo { it.datePosted }
    val lastDateUpdated = varchar("lastDateUpdated").bindTo { it.lastDateUpdated }
    val authorId = varchar("authorId").bindTo { it.authorId }
}

interface DBPostEntity: Entity<DBPostEntity> {

    companion object : Entity.Factory<DBPostEntity>()

    val id: String
    var name: String
    var title: String
    var content: String
    var authorId: String
    val datePosted :String
    var lastDateUpdated :String
}