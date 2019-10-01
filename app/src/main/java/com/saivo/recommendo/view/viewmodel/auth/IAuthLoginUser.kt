package com.saivo.recommendo.view.viewmodel.auth

import android.view.View
import android.widget.EditText
import com.saivo.recommendo.data.objects.LoginCredentials

interface IAuthLoginUser: IValidation {
    fun userLoggedIn(view: View)
    fun registerUser(view: View)
    fun resetUserPassword(view: View)
    fun getLoginCredentials(): LoginCredentials
    fun setLoginCredentials(loginCredentials: LoginCredentials)
}