package com.example.database.entities

import org.ktorm.entity.Entity
import org.ktorm.schema.*

object DBClassTable: Table<DBClassEntity>("class") {

    val id = long("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val teacherId = long("teacherId").references(DBTeacherTable){ it.teacherId }
    val schoolId = long("schoolId").references(DBSchoolTable){ it.schoolId }
    val stage = int("stage").bindTo { it.stage }

}

interface DBClassEntity: Entity<DBClassEntity> {

    companion object : Entity.Factory<DBClassEntity>()

    val id:Long
    var name:String
    val teacherId :DBTeacherEntity
    var schoolId: DBSchoolEntity
    val stage :Int
}