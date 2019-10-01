package com.saivo.recommendo.view.viewmodel.auth

import android.widget.EditText

interface IValidation {
    fun emailValidation(editText: EditText, cb: (boolean: Boolean) -> Unit)
    fun passwordValidation(
        editText: EditText,
        cb: ((boolean: Boolean) -> Unit)? = null,
        form: Array<EditText>? = null
    ): Boolean
}