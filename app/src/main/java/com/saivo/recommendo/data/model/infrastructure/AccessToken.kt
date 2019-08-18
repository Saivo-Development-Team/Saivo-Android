package com.saivo.recommendo.data.model.infrastructure

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class AccessToken(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("scope")
    val scope: String,
    @SerializedName("token_type")
    val tokenType: String
)