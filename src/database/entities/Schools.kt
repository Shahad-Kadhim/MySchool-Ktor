package com.example.database.entities

import org.jetbrains.exposed.sql.Table


object Schools: Table("School") {

    val id = varchar("id",50).primaryKey()
    val name = varchar("name",20).uniqueIndex()
    val mangerId = (varchar("mangerId",50) references Mangers.id)

}
