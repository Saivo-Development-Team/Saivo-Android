package com.saivo.recommendo.data.objects

data class ClientDetails(
    val accessTokenValidity: Int,
    val authorizedGrantTypes: String ,
    val clientId: String ,
    val clientSecret: String,
    val refreshTokenValidity: Int,
    val resourceIds: String,
    val scope: String 
)