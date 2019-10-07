package com.saivo.recommendo.data.model.domain

data class Notification(
    val rated: String,
    val title: String,
    val rating: String,
    val stars: Double,
    val users: Int,
    val image: String
)