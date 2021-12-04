package com.example.database.entities

import org.ktorm.entity.Entity
import org.ktorm.schema.*

object DBClassTable: Table<DBClassEntity>("class") {

    val id = varchar("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val teacherId = varchar("teacherId").bindTo { it.teacherId }
    val schoolId = varchar("schoolId").bindTo { it.schoolId }
    val stage = int("stage").bindTo { it.stage }

}

interface DBClassEntity: Entity<DBClassEntity> {

    companion object : Entity.Factory<DBClassEntity>()

    val id:String
    var name:String
    var teacherId :String
    var schoolId: String
    val stage :Int
}