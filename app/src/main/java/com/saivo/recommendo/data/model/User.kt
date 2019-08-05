package com.saivo.recommendo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    val email: String,
    val lastname: String,
    val firstname: String,

    val roles: List<Any> = listOf(),
    val preferences: List<Any> = listOf(),
    val recommendations: List<Any> = listOf()
)