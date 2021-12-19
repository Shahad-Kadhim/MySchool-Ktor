package com.example.database.entities

import org.ktorm.entity.Entity
import org.ktorm.schema.*

object DBMemberClassTable: Table<DBMemberClassEntity>("member-class") {

    val studentId = long("studentId").references(DBStudentTable){ it.studentId }
    val classId = long("classId").references(DBClassTable){ it.classId }
    val dateJoined = varchar("dateJoined").bindTo { it.dateJoined }

}

interface DBMemberClassEntity: Entity<DBMemberClassEntity> {

    companion object : Entity.Factory<DBMemberClassEntity>()

    val studentId:DBStudentEntity
    val classId: DBClassEntity
    val dateJoined: String
}