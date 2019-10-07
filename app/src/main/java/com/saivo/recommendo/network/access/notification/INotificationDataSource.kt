package com.saivo.recommendo.network.access.notification

import com.saivo.recommendo.data.model.domain.Notification

interface INotificationDataSource {
    suspend fun getRecommendationOfActivity(title: String): Notification
    suspend fun getNotificationForUserAsync(email: String): List<Notification>
}