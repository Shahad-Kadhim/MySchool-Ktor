package com.example.database.entities

import org.jetbrains.exposed.sql.Table

object Roles: Table("role") {
    val role =varchar("role",10).primaryKey()
}