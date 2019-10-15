package com.saivo.recommendo.data.model.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_preference")
data class Preference(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val likes: String,
    val dislikes: String,
    val category: String,
    val description: String
)