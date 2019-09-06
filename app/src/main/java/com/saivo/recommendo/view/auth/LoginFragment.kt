package com.saivo.recommendo.view.auth


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saivo.recommendo.R
import com.saivo.recommendo.network.access.user.IUserDataSource
import com.saivo.recommendo.util.helpers.*
import com.saivo.recommendo.util.helpers.Status.LOGIN_SUCCESSFUL
import com.saivo.recommendo.util.helpers.Status.LOGIN_UNSUCCESSFUL
import com.saivo.recommendo.view.main.CoroutineFragment
import com.saivo.recommendo.view.viewable.auth.AuthViewModel
import com.saivo.recommendo.view.viewable.auth.AuthViewModelFactory
import com.saivo.recommendo.view.viewable.auth.IAuthLoginUser
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class LoginFragment : CoroutineFragment(), KodeinAware {
    override val kodein by closestKodein()
    private lateinit var authViewModel: IAuthLoginUser
    private val userDataSource: IUserDataSource by instance()
    private val authViewModelFactory: AuthViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = ViewModelProvider(this, authViewModelFactory).get(AuthViewModel::class.java)
        val form: Array<EditText> = arrayOf(login_password_editText, login_email_editText)

        fun switchButton(boolean: Boolean) {
            login_button.isEnabled = boolean
        }


        fun validateInput(input: EditText) {
            input.apply {
                if (text.isEmpty()) error = "$hint is required"
                when (id) {
                    login_email_editText.id -> authViewModel.emailValidation(this, ::switchButton)
                    login_password_editText.id -> if (!isValidInput(makeString(text), ::isNotEmpty)
                    ) switchButton(false) else switchButton(true)
                }
            }
        }


        for (input in form) input.apply {
            addTextChangedListener(textWatcher(onText = {
                validateInput(this)
            }, after = {
                authViewModel.apply {
                    setLoginCredentials(getLoginCredentials().apply {
                        makeString(text).also { value ->
                            when (id) {
                                login_email_editText.id -> email = value
                                login_password_editText.id -> password = value
                            }
                        }
                    })
                }
            }))
        }

        login_button.setOnClickListener {
            launch {
                with(userDataSource) { loginUserAsync(authViewModel.getLoginCredentials()) }.apply {
                    when (status) {
                        LOGIN_SUCCESSFUL -> {
                            authViewModel.userLoggedIn(view)
                        }
                        LOGIN_UNSUCCESSFUL ->
                            Display.toastMessage(this@LoginFragment.context, message)
                        else -> Display.toastMessage(this@LoginFragment.context, message)
                    }
                }
            }
        }

        login_register_button.setOnClickListener {
            authViewModel.registerUser(view)
        }

        login_reset_password_button.setOnClickListener {
            authViewModel.resetUserPassword(view)
        }
    }
}

