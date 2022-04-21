package com.example.database.entities

import org.jetbrains.exposed.sql.Table

object NotificationUser: Table("notification_user") {
    val notificationId = varchar("notification_id",50) references Notifications.id
    val userId = varchar("user_id",100) references Users.id
    init {
        index(true, notificationId, userId)
    }
}