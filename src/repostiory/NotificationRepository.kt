package com.example.repostiory

import com.example.dao.NotificationDao
import com.example.models.Notification

class NotificationRepository(
    private val notificationDao: NotificationDao
) {

    fun createNotification(notification: Notification,users: List<String>){
        notificationDao.createNotification(notification,users)
    }

    fun getNotifications(userId: String) =
        notificationDao.getNotifications(userId)

}