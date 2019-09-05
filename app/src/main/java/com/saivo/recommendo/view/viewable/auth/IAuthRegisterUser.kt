package com.saivo.recommendo.view.viewable.auth

import android.view.View
import android.widget.EditText
import com.saivo.recommendo.data.objects.RegisterCredentials

interface IAuthRegisterUser {
    fun userRegistered(view: View)
    fun emailValidation(editText: EditText, cb: (boolean: Boolean) -> Unit)
    fun passwordValidation(
        editText: EditText,
        cb: (boolean: Boolean) -> Unit,
        form: Array<EditText>
    )
    fun getRegisterCredentials() : RegisterCredentials
    fun setRegisterCredentials(registerCredentials: RegisterCredentials)
}