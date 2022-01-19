package com.example.database

import org.jetbrains.exposed.sql.Database


class DatabaseManager {

    private val database = Database.connect(
        url = "jdbc:mysql://localhost:3306/my_school",
        driver = "org.h2.Driver",
        user = "root",
        password = ""
    )

    fun getDatabase() = database

}