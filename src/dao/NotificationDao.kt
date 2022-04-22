package com.example.dao

import com.example.database.entities.NotificationUser
import com.example.database.entities.Notifications
import com.example.firebase.Data
import com.example.firebase.NotificationBody
import com.example.firebase.NotificationService
import com.example.models.Notification
import com.example.util.DataClassParser
import com.example.util.addNotifications
import com.example.util.insertNotification
import com.example.util.toNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class NotificationDao(
    private val userDao: UserDao,
    private val notificationService: NotificationService,
    private val dataClassParser: DataClassParser
) {

    fun createNotification(notification: Notification, users: List<String>){
        transaction {
            Notifications.insertNotification(notification)
            publishNotifications(notification.id, users)
            sentNotification(notification,users)
        }
    }

    private fun publishNotifications(notificationId: String, users: List<String>){
        NotificationUser.addNotifications(notificationId,users)
    }


    private fun sentNotification(notification: Notification, users: List<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            notificationService.sentNotification(
                dataClassParser.parseToJson(
                    NotificationBody(
                        Data(notification.title, notification.content),
                        userDao.getFirebaseToken(users)
                    )
                )
            )
        }
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