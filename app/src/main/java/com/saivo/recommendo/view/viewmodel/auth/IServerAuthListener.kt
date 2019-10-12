package com.saivo.recommendo.view.viewmodel.auth

import com.saivo.recommendo.data.model.infrastructure.Token

interface IServerAuthListener {
    fun onInit()
    fun onAccess()
    fun onRegister()
    fun onRegistered()
    fun onCreateToken(block: () -> Any)
    fun onTokenCreated(block: ((token: Token) -> Any) -> Unit)
}
