package com.saivo.recommendo.data.model.domain

import androidx.room.*
import org.threeten.bp.ZonedDateTime

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val user: String,
    val email: String,
    val enabled: Boolean,
    val lastname: String,
    val firstname: String
)