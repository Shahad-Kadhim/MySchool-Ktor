package com.example.database.entities

import org.ktorm.entity.Entity
import org.ktorm.schema.*

object DBMemberClassTable: Table<DBMemberClassEntity>("MemberClass") {

    val studentId = varchar("studentId").primaryKey().bindTo { it.studentId }
    val classId = varchar("classId").bindTo { it.classId }
    val dateJoined = varchar("dateJoined").bindTo { it.dateJoined }

}

interface DBMemberClassEntity: Entity<DBMemberClassEntity> {

    companion object : Entity.Factory<DBMemberClassEntity>()

    val studentId:String
    val classId: String
    val dateJoined: String
}