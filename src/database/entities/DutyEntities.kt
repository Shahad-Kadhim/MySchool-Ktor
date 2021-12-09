package com.example.database.entities

import org.ktorm.entity.Entity
import org.ktorm.schema.*

object DBDutyTable: Table<DBDutyEntity>("Duty") {

    val id = varchar("id").primaryKey().bindTo { it.id }
    val studentId = varchar("name").bindTo { it.studentID }
    val title = varchar("password").bindTo { it.title }
    val content = varchar("name").bindTo { it.content }

}

interface DBDutyEntity: Entity<DBDutyEntity> {

    companion object : Entity.Factory<DBDutyEntity>()

    val id: String
    val state: String  // DONE , TO_DO or Missing
    val studentID: String
    var title: String
    var content: String
}