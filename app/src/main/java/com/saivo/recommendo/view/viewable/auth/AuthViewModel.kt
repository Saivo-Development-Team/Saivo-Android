package com.saivo.recommendo.view.viewable.auth

import android.view.View
import android.widget.EditText
import com.saivo.recommendo.data.objects.LoginCredentials
import com.saivo.recommendo.data.objects.RegisterCredentials
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.saivo.recommendo.R
import com.saivo.recommendo.util.helpers.formNotEmpty
import com.saivo.recommendo.util.helpers.isEmail
import com.saivo.recommendo.util.helpers.isPassword


class AuthViewModel : ViewModel(), IAuthRegisterUser, IAuthLoginUser {
    private lateinit var loginCredentials: LoginCredentials
    private lateinit var registerCredentials: RegisterCredentials

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
        cb: (boolean: Boolean) -> Unit,
        form: Array<EditText>
    ) {
        editText.text.toString().trim().also { p ->
            isPassword(p).also {
                when {
                    p.length <= 8 -> cb(false)
                    p.length >= 8 -> if (!it) {
                        editText.error = "Try a Stronger ${editText.hint} e.g [ P@ssword5 ]"
                        cb(false)
                    } else cb(formNotEmpty(form))
                }
            }
        }
    }

    override fun userLoggedIn(view: View) {
        Navigation.findNavController(view).navigate(R.id.login_to_main_ui_action)
    }

    override fun registerUser(view: View) {
        Navigation.findNavController(view).navigate(R.id.login_to_register_action)
    }

    override fun resetUserPassword(view: View) {
        Navigation.findNavController(view).navigate(R.id.login_to_reset_password_action)
    }

    override fun userRegistered(view: View) {
        Navigation.findNavController(view).navigate(R.id.register_to_main_ui_action)
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