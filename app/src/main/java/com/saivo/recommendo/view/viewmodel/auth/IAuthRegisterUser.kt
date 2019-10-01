package com.saivo.recommendo.view.viewmodel.auth

import android.view.View
import android.widget.EditText
import com.saivo.recommendo.data.objects.RegisterCredentials

interface IAuthRegisterUser: IValidation {
    fun userRegistered(view: View)
    fun getRegisterCredentials() : RegisterCredentials
    fun setRegisterCredentials(registerCredentials: RegisterCredentials)
}