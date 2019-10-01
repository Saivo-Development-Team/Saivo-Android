package com.saivo.recommendo.data.model.infrastructure

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.saivo.recommendo.util.helpers.TOKEN_ID
import org.threeten.bp.ZonedDateTime

@Entity(tableName = "token")
data class Token(
    @PrimaryKey(autoGenerate = false)
    val deviceToken: Int = TOKEN_ID,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String?,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("scope")
    val scope: String
)