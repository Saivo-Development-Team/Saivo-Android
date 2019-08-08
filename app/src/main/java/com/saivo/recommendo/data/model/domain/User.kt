package com.saivo.recommendo.data.model.domain

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",

    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("lastname")
    val lastname: String,

    @SerializedName("firstname")
    val firstname: String,

    @Embedded(prefix = "preference_")
    @SerializedName("preferences")
    val preferences: List<Preference> = listOf(),

    @Embedded(prefix = "recommendation_")
    @SerializedName("recommendations")
    val recommendations: List<Recommendation> = listOf()
)