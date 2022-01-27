package com.example.database

import org.jetbrains.exposed.sql.Database
import java.net.URI


class DatabaseManager {
    private var host: String
    private var port: Int =0
    private var databaseName: String
    private var databaseUserName: String
    private var databasePassword: String

    init {
        System.getenv("DATABASE_URL").apply {
            if(this!=null){
                URI(this).also {
                    host = it.host
                    port = it.port
                    databaseName = it.path.substring(1)
                    it.userInfo.split(':').also { info ->
                        databaseUserName= info[0]
                        databasePassword =info[1]
                    }
                }
            }else{
                host= System.getenv("DB_HOST")
                port= System.getenv("DB_PORT").toInt()
                databaseName= System.getenv("DB_NAME")
                databaseUserName= System.getenv("DB_USER")
                databasePassword= System.getenv("DB_PASSWORD")

            }
        }
    }
    private val database = Database.connect(
        url = "jdbc:postgresql://$host:$port/$databaseName",
        driver = "org.postgresql.Driver",
        user = databaseUserName,
        password = databasePassword
    )

    fun getDatabase() = database

}