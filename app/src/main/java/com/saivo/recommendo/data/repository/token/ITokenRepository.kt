package com.saivo.recommendo.data.repository.token

interface ITokenRepository {
     fun getAccessToken(): String
     fun getTokenType(): String
}