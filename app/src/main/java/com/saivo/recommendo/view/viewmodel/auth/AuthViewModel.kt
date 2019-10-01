package com.saivo.recommendo.view.viewmodel.auth

import android.view.View
import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.saivo.recommendo.R
import com.saivo.recommendo.data.objects.LoginCredentials
import com.saivo.recommendo.data.objects.RegisterCredentials
import com.saivo.recommendo.util.helpers.formNotEmpty
import com.saivo.recommendo.util.helpers.isEmail
import com.saivo.recommendo.util.helpers.isPassword


class AuthViewModel : ViewModel(), IAuthRegisterUser, IAuthLoginUser, IAuthRestPassword {
    private lateinit var loginCredentials: LoginCredentials
    private lateinit var registerCredentials: RegisterCredentials
    private lateinit var otpNumberValue: String

    override fun emailValidation(editText: EditText, cb: (boolean: Boolean) -> Unit) {
        editText.text.toString().trim().also { e ->
            when {
                !isEmail(e) -> {
                    editText.error = "Enter valid ${editText.hint}"
                    cb(false)
                }
            }
        }
    }

    override fun passwordValidation(
        editText: EditText,
        cb: ((boolean: Boolean) -> Unit)?,
        form: Array<EditText>?
    ): Boolean {
        editText.text.toString().trim().also { p ->
            isPassword(p).also {
                when {
                    p.length <= 8 -> cb?.invoke(false)
                    p.length >= 8 -> if (!it) {
                        editText.error = "Try a Stronger ${editText.hint} e.g [ P@ssword5 ]"
                        cb?.invoke(false)
                    } else form?.apply { cb?.invoke(formNotEmpty(this)) }
                }
                return it
            }
        }
    }

    override fun setNewOTPNumber(pin: String) {
        otpNumberValue = pin
    }

    override fun checkOTPMatch(pin: String): Boolean {
        return otpNumberValue == pin
    }

    override fun getOTPNumber(): String {
        return otpNumberValue
    }

    override fun userLoggedIn(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_login_to_home)
    }

    override fun registerUser(view: View) {
        Navigation.findNavController(view).navigate(R.id.login_to_register_action)
    }

    override fun resetUserPassword(view: View) {
        Navigation.findNavController(view).navigate(R.id.login_to_reset_password_action)
    }

    override fun userRegistered(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_register_to_home)
    }

    override fun popToLogin(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_reset_password_pop)
    }

    override fun setRegisterCredentials(registerCredentials: RegisterCredentials) {
        this.registerCredentials = registerCredentials
    }

    override fun setLoginCredentials(loginCredentials: LoginCredentials) {
        this.loginCredentials = loginCredentials
    }

    override fun getRegisterCredentials(): RegisterCredentials {
        if (::registerCredentials.isInitialized)
            return this.registerCredentials
        return RegisterCredentials()
    }

    override fun getLoginCredentials(): LoginCredentials {
        if (::loginCredentials.isInitialized)
            return this.loginCredentials
        return LoginCredentials()
    }
}