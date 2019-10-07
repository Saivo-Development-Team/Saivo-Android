package com.saivo.recommendo.network.access.notification

import com.saivo.recommendo.data.model.domain.Notification
import com.saivo.recommendo.network.INetworkService
import com.saivo.recommendo.network.resquest.INotificationService

class NotificationDataSource(private val notificationService: INotificationService) : INotificationDataSource {
    override suspend fun getNotificationForUserAsync(email: String): List<Notification> {
        return notificationService.getNotificationForUserAsync(email).await()
    }

    override suspend fun getRecommendationOfActivity(title: String): Notification {
     return notificationService.getRecommendationOfActivity(title).await()
    }

}