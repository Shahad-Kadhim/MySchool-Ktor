package com.example.database.entities

import org.ktorm.entity.Entity
import org.ktorm.schema.*

object DBDutyTable: Table<DBDutyEntity>("Duty") {

    val id = long("id").primaryKey().bindTo { it.id }
    val studentId = long("name").references(DBStudentTable){ it.studentID }
    val title = varchar("password").bindTo { it.title }
    val content = varchar("name").bindTo { it.content }

}

interface DBDutyEntity: Entity<DBDutyEntity> {

    companion object : Entity.Factory<DBDutyEntity>()

    val id: Long
    val state: String  // DONE , TO_DO or Missing
    val studentID: DBStudentEntity
    var title: String
    var content: String
}