package com.example.database.entities

import org.ktorm.entity.Entity
import org.ktorm.schema.*

object DBSchoolTable: Table<DBSchoolEntity>("School") {

    val id = varchar("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val mangerId = varchar("mangerId").bindTo { it.mangerId }

}

interface DBSchoolEntity: Entity<DBSchoolEntity> {

    companion object : Entity.Factory<DBSchoolEntity>()

    val id: String
    var name: String
    var mangerId: String
}