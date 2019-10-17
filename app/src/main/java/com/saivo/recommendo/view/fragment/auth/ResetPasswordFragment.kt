package com.saivo.recommendo.view.fragment.auth


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.saivo.recommendo.R
import com.saivo.recommendo.network.access.user.IUserDataSource
import com.saivo.recommendo.util.helpers.toastMessage
import com.saivo.recommendo.view.fragment.CoroutineFragment
import com.saivo.recommendo.view.viewmodel.ViewModelFactory
import com.saivo.recommendo.view.viewmodel.auth.AuthViewModel
import com.saivo.recommendo.view.viewmodel.auth.IAuthRestPassword
import kotlinx.android.synthetic.main.fragment_reset_password.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class ResetPasswordFragment : CoroutineFragment(), KodeinAware {
    override val kodein by closestKodein()
    private lateinit var authViewModel: IAuthRestPassword
    private val userDataSource: IUserDataSource by instance()
    private val authViewModelFactory: ViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reset_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel = ViewModelProvider(this, authViewModelFactory).get(AuthViewModel::class.java)

        reset_confirm_button.setOnClickListener {
            runCatching {
                when {
                    email_number_input_group.isVisible -> getUserInfo()
                    otp_input_group.isVisible -> showOtpPad()
                    reset_password_editText.isVisible -> changePassword()
                    else -> Unit
                }
            }
        }

    }

    private fun changePassword() = launch {
        authViewModel.apply {
            reset_password_editText.apply {
                when {
                    passwordValidation(this) -> {
                        resetUserPassword(
                            password = text.toString(),
                            email = reset_email_editText.text.toString()
                        )
                        toLogin(this@ResetPasswordFragment.requireView())
                    }
                }
            }
        }
    }

    private fun showOtpPad() = launch {
        if (checkOTPValues()) {
            otp_input_group.visibility = View.GONE
            reset_password_editText.visibility = View.VISIBLE
            reset_confirm_button.setText(R.string.change_password)
        }
    }

    private fun getUserInfo() {
        initRestPassword().invokeOnCompletion {
            if (authViewModel.canChange) {
                email_number_input_group.visibility = View.GONE
                otp_input_group.visibility = View.VISIBLE
            }
        }
    }

    private suspend fun checkOTPValues() = withContext(this.coroutineContext) {
        authViewModel.checkOTPMatch(otp_input_view.text.toString())
    }

    private fun initRestPassword() = launch {
        setOTPNumber(
            reset_phone_number_editText.text.toString(),
            reset_email_editText.text.toString()
        )
    }

    private suspend fun resetUserPassword(password: String, email: String) = withContext(IO) {
        userDataSource.restUserPassword(password, email)
    }

    private suspend fun setOTPNumber(number: String, email: String) = withContext(IO) {
        runCatching {
            return@withContext authViewModel.setNewOTPNumber(sendSMS(number, email))
        }.onFailure {
            launch(Main) {
                toastMessage(this@ResetPasswordFragment.context, it.message.toString())
            }
            authViewModel.canChange = false
        }.onSuccess {
            authViewModel.canChange = true
        }
    }

    private suspend fun sendSMS(number: String, email: String): String = withContext(IO) {
        with(userDataSource.getOTPFromServer(number = number, email = email)) {
            if (!error.isNotBlank()) return@withContext (getObject<String>())
            else throw Exception(message)
        }
    }
}
