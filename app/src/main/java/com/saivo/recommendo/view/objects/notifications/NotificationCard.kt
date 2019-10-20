package com.saivo.recommendo.view.objects.notifications

data class NotificationCard (
    val rated: String,
    val title: String,
    val rating: String,
    val stars: Double,
    val users: Int,
    val image: String
)
