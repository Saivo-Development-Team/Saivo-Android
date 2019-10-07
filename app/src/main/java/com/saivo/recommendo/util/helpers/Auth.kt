package com.saivo.recommendo.util.helpers

import com.saivo.recommendo.data.access.TokenDao

fun token(tokenDao: TokenDao): String {
    tokenDao.let { return it.getTokenType() + it.getAccessToken() }
}

fun basicAuth(id: String, secret: String): String {
    return "Basic ${base64encode(createByteArray("${id}:${secret}"))}"
}