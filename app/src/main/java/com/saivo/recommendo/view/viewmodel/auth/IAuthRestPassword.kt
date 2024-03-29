package com.saivo.recommendo.view.viewmodel.auth

import android.view.View

interface IAuthRestPassword: IValidation {
    var canChange: Boolean
    fun setNewOTPNumber(pin: String)
    fun checkOTPMatch(pin: String): Boolean
    fun getOTPNumber(): String
    fun toLogin(view: View)
}
