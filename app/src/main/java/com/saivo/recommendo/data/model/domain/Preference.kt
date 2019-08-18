package com.saivo.recommendo.data.model.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_preference")
data class Preference(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prefer: String,
    val disfavor: String,
    val description: String
)