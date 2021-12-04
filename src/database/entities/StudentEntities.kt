package com.example.database

import org.ktorm.entity.Entity
import org.ktorm.schema.*

object DBStudentTable: Table<DBStudentEntity>("student") {

    val id = varchar("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val password = varchar("password").bindTo { it.password }
    val age = int("age").bindTo { it.age }
    val note = text("note").bindTo { it.note }
    val phone = int("phone").bindTo { it.phone }
    val stage = int("stage").bindTo { it.stage }

}

interface DBStudentEntity: Entity<DBStudentEntity> {

    companion object : Entity.Factory<DBStudentEntity>()

    val id: String
    var name: String
    var password: String
    var age: Int
    var note: String
    var phone: Int
    var stage: Int
}