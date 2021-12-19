package com.example.database.entities

import org.ktorm.entity.Entity
import org.ktorm.schema.*

object DBSchoolTable: Table<DBSchoolEntity>("School") {

    val id = long("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val mangerId = long("mangerId").references(DBMangerTable){ it.mangerId }

}

interface DBSchoolEntity: Entity<DBSchoolEntity> {

    companion object : Entity.Factory<DBSchoolEntity>()

    val id: Long
    var name: String
    var mangerId: DBMangerEntity
}