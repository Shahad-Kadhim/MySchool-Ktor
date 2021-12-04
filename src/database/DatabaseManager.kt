package com.maaxgr.database

import org.ktorm.database.Database

class DatabaseManager {

    private val database = Database.connect(
        url = "jdbc:mysql://localhost:3306/my_school?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "root",
        password = ""
    )

    fun getDatabase() = database

}