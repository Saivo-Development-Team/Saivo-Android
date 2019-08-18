package com.saivo.recommendo.data.model.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rating")
data class Rating(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val stars: Int,
    val comment: String,
    val written: String
)