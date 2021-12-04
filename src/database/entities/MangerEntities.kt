package com.example.database.entities

import org.ktorm.entity.Entity
import org.ktorm.schema.*

object DBMangerTable: Table<DBMangerEntity>("Manger") {

    val id = varchar("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val schoolId = varchar("schoolId").bindTo { it.schoolId }

}

interface DBMangerEntity: Entity<DBMangerEntity> {

    companion object : Entity.Factory<DBMangerEntity>()

    val id: String
    var name: String
    var schoolId: String
}