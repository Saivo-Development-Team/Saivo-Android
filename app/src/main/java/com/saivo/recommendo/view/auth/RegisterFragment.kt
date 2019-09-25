package com.saivo.recommendo.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.saivo.recommendo.R
import com.saivo.recommendo.network.access.IUserDataSource
import com.saivo.recommendo.util.helpers.*
import com.saivo.recommendo.util.helpers.Status.REGISTRATION_SUCCESSFUL
import com.saivo.recommendo.view.main.CoroutineFragment
import com.saivo.recommendo.view.viewable.auth.AuthViewModel
import com.saivo.recommendo.view.viewable.auth.AuthViewModelFactory
import com.saivo.recommendo.view.viewable.auth.IAuthRegisterUser
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class RegisterFragment : CoroutineFragment(), KodeinAware {
    override val kodein by closestKodein()
    private lateinit var authViewModel: IAuthRegisterUser
    private val userDataSource: IUserDataSource by instance()
    private val authViewModelFactory: AuthViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = ViewModelProvider(this, authViewModelFactory).get(AuthViewModel::class.java)
        val form: Array<EditText> = arrayOf(
            register_email_editText, register_password_editText,
            register_lastname_editText, register_firstname_editText
        )

        fun switchButton(boolean: Boolean) {
            register_register_button.isEnabled = boolean
        }

        fun validateInput(input: EditText) {
            input.apply {
                if (text.isEmpty()) error = "$hint is required"
                when (id) {
                    register_email_editText.id -> authViewModel.emailValidation(
                        this, ::switchButton
                    )
                    register_password_editText.id -> authViewModel.passwordValidation(
                        this, ::switchButton, form
                    )
                }
            }
        }

        for (input in form) input.apply {
            addTextChangedListener(textWatcher(onText = {
                switchButton(formNotEmpty(form))
                validateInput(this)
            }, after = {
                authViewModel.apply {
                    setRegisterCredentials(getRegisterCredentials().apply {
                        makeString(text).also { value ->
                            when (id) {
                                register_email_editText.id -> email = value
                                register_password_editText.id -> password = value
                                register_lastname_editText.id -> lastname = value
                                register_firstname_editText.id -> firstname = value
                            }
                        }
                    })
                }
            }))
        }

        register_register_button.setOnClickListener {
            launch {
                println("[register_register_button.setOnClickListener]: ${authViewModel.getRegisterCredentials()}")
                with(userDataSource) { registerUserAsync(authViewModel.getRegisterCredentials()) }.apply {
                    when (status) {
                        REGISTRATION_SUCCESSFUL -> {
                            with(userDataSource) { getUserDataAsync(data as String) }
                            authViewModel.userRegistered(view)
                        }
                        else -> toastMessage(this@RegisterFragment.context, message)
                    }
                }
            }
        }
    }
}