package com.saivo.recommendo.data.objects

data class ClientDetails(
    val scope: String,
    val clientId: String ,
    val resourceIds: String,
    val clientSecret: String,
    val accessTokenValidity: Int,
    val refreshTokenValidity: Int,
    val authorizedGrantTypes: String
)