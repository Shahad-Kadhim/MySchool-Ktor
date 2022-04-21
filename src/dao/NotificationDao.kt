package com.example.dao

import com.example.database.entities.NotificationUser
import com.example.database.entities.Notifications
import com.example.models.Notification
import com.example.util.addNotifications
import com.example.util.insertNotification
import com.example.util.toNotification
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class NotificationDao {

    fun createNotification(notification: Notification, users: List<String>){
        transaction {
            Notifications.insertNotification(notification)
            publishNotifications(notification.id, users)
        }
    }

    private fun publishNotifications(notificationId: String, users: List<String>){
        NotificationUser.addNotifications(notificationId,users)
    }

    fun getNotifications(userId: String) =
        transaction {
            Notifications.select(
                Notifications.id.inList(getNotificationsId(userId))
            ).map {
                it.toNotification()
            }
        }


    private fun getNotificationsId(userId: String): List<String> =
        transaction{
            NotificationUser
                .select(NotificationUser.userId.eq(userId))
                .map { it[NotificationUser.notificationId] }
        }
}