package com.saivo.recommendo.data.model.domain

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_recommendation")
data class Recommendation(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val date: String,
    @Embedded(prefix = "activity_")
    val activity: Activity
)