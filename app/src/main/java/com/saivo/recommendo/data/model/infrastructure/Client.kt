package com.saivo.recommendo.data.model.infrastructure


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "client")
data class Client(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("clientId")
    val clientId: String = "",
    @SerializedName("accessTokenValidity")
    val accessTokenValidity: Int = 0,
    @SerializedName("additionalInformation")
    val additionalInformation: String = "",
    @SerializedName("authorities")
    val authorities: String = "",
    @SerializedName("authorizedGrantTypes")
    val authorizedGrantTypes: String = "",
    @SerializedName("autoapprove")
    val autoapprove: String = "",
    @SerializedName("refreshTokenValidity")
    val refreshTokenValidity: Int = 0,
    @SerializedName("resourceIds")
    val resourceIds: String = "",
    @SerializedName("scope")
    val scope: String = "",
    @SerializedName("webServerRedirectUri")
    val webServerRedirectUri: String = ""
)