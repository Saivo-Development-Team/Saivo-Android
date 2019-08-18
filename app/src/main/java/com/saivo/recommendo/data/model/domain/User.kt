package com.saivo.recommendo.data.model.domain

import androidx.room.*

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val email: String,
    val enabled: Boolean,
    val lastname: String,
    val firstname: String
)