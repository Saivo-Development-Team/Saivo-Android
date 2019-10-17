package com.saivo.recommendo.view.fragment.auth


import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.saivo.recommendo.R
import com.saivo.recommendo.network.access.user.IUserDataSource
import com.saivo.recommendo.util.helpers.*
import com.saivo.recommendo.view.fragment.CoroutineFragment
import com.saivo.recommendo.view.viewmodel.ViewModelFactory
import com.saivo.recommendo.view.viewmodel.auth.AuthViewModel
import com.saivo.recommendo.view.viewmodel.auth.IAuthLoginUser
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class LoginFragment : CoroutineFragment(), KodeinAware {
    override val kodein by closestKodein()
    private lateinit var authViewModel: IAuthLoginUser
    private val userDataSource: IUserDataSource by instance()
    private val viewModelFactory: ViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel = ViewModelProvider(this, viewModelFactory).get(AuthViewModel::class.java)
        initLogin(view)
    }

    private fun switchButton(boolean: Boolean) {
        login_button.isEnabled = boolean
    }

    private fun initLogin(view: View) {
        val form: Array<EditText> = arrayOf(login_password_editText, login_email_editText)

        for (input in form) input.apply {
            addTextChangedListener(
                textWatcher(
                    onText = { checkInputs(input, this@LoginFragment) }, after = checkValues()
                )
            )
        }

        login_button.setOnClickListener { launch { loginUser(view) } }

        with(authViewModel) {
            login_register_button.setOnClickListener { registerUser(view) }

            login_reset_password_button.setOnClickListener { resetUserPassword(view) }
        }
    }

    private fun checkInputs(editText: EditText, loginFragment: LoginFragment) {
        if (editText.text.isEmpty()) editText.error = "${editText.hint} is required"
        when (editText.id) {
            login_email_editText.id -> loginFragment.authViewModel.emailValidation(
                editText, ::switchButton
            )
            login_password_editText.id -> if (!isValidInput(
                    makeString(editText.text), ::isNotEmpty
                )
            ) switchButton(false) else switchButton(true)
        }
    }

    private suspend fun loginUser(view: View) {
        with(userDataSource) { loginUserAsync(authViewModel.getLoginCredentials()) }.apply {
            when (status) {
                LOGIN_SUCCESSFUL -> {
                    authViewModel.userLoggedIn(view)
                }
                LOGIN_UNSUCCESSFUL ->
                    toastMessage(this@LoginFragment.context, message)
                else -> toastMessage(this@LoginFragment.context, message)
            }
        }
    }

    private fun EditText.checkValues(): (edit: Editable?) -> Unit = {
        with(authViewModel) {
            setLoginCredentials(getLoginCredentials().apply {
                makeString(text).let {
                    when (id) {
                        login_email_editText.id -> email = it
                        login_password_editText.id -> password = it
                    }
                }
            })
        }
    }

}

