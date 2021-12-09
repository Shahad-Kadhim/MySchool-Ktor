package com.example.database.entities

import org.ktorm.entity.Entity
import org.ktorm.schema.*

object DBTeacherTable: Table<DBTeacherEntity>("Teacher") {

    val id = varchar("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val password = varchar("password").bindTo { it.password }
    val teachingSpecialization = varchar("teachingSpecialization").bindTo { it.teachingSpecialization }
    val phone = int("phone").bindTo { it.phone }

}

interface DBTeacherEntity: Entity<DBTeacherEntity> {

    companion object : Entity.Factory<DBTeacherEntity>()

    val id: String
    var name: String
    var password: String
    var teachingSpecialization: String
    var phone: Int
}