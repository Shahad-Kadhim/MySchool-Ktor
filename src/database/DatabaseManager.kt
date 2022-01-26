package com.example.database

import org.jetbrains.exposed.sql.Database


class DatabaseManager {


    private val host= System.getenv("DB_HOST")
    private val port= System.getenv("DB_PORT")
    private val databaseName= System.getenv("DB_NAME")
    private val databaseUserName= System.getenv("DB_USER")
    private val databasePassword= System.getenv("DB_PASSWORD")

    private val database = Database.connect(
        url = "jdbc:postgresql://$host:$port/$databaseName",
        driver = "org.postgresql.Driver",
        user = databaseUserName,
        password = databasePassword
    )

    fun getDatabase() = database

}