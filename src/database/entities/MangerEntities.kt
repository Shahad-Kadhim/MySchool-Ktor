package com.example.database.entities

import org.ktorm.entity.Entity
import org.ktorm.schema.*

object DBMangerTable: Table<DBMangerEntity>("manger") {

    val id = long("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val schoolId = varchar("schoolId").references(DBSchoolTable){ it.schoolId }

}

interface DBMangerEntity: Entity<DBMangerEntity> {

    companion object : Entity.Factory<DBMangerEntity>()

    val id: Long
    var name: String
    var schoolId: DBSchoolEntity
}