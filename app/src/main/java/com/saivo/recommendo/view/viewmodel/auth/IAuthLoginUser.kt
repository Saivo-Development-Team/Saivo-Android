package com.saivo.recommendo.view.viewmodel.auth

import android.view.View
import android.widget.EditText
import com.saivo.recommendo.data.objects.LoginCredentials

interface IAuthLoginUser {
    fun userLoggedIn(view: View)
    fun registerUser(view: View)
    fun emailValidation(editText: EditText, cb: (boolean: Boolean) -> Unit)
    fun resetUserPassword(view: View)
    fun passwordValidation(
        editText: EditText,
        cb: (boolean: Boolean) -> Unit,
        form: Array<EditText>
    )
    fun getLoginCredentials(): LoginCredentials
    fun setLoginCredentials(loginCredentials: LoginCredentials)
}