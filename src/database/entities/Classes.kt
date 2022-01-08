package com.example.database.entities


import org.jetbrains.exposed.sql.Table


object Classes: Table("class") {

    val id = varchar("id",50).primaryKey()
    val name = varchar("name",30)
    val teacherId = (varchar("teacherId",50) references Teachers.id)
    val schoolId = (varchar("schoolId",50) references  Schools.id)
    val stage = integer("stage")

}
