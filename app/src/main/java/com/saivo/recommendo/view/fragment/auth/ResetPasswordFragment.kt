package com.saivo.recommendo.view.fragment.auth


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.saivo.recommendo.R
import com.saivo.recommendo.network.access.IUserDataSource
import com.saivo.recommendo.view.fragment.CoroutineFragment
import com.saivo.recommendo.view.viewmodel.auth.AuthViewModel
import com.saivo.recommendo.view.viewmodel.auth.AuthViewModelFactory
import com.saivo.recommendo.view.viewmodel.auth.IAuthRestPassword
import kotlinx.android.synthetic.main.fragment_reset_password.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class ResetPasswordFragment : CoroutineFragment(), KodeinAware {
    override val kodein by closestKodein()
    private lateinit var authViewModel: IAuthRestPassword

    private val userDataSource: IUserDataSource by instance()
    private val authViewModelFactory: AuthViewModelFactory by instance()

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
                    email_number_input_group.isVisible -> {
                        initRestPassword().invokeOnCompletion {
                            email_number_input_group.visibility = View.GONE
                            otp_input_group.visibility = View.VISIBLE
                        }
                    }
                    otp_input_group.isVisible -> launch {
                        if (checkOTPValues()) {
                            otp_input_group.visibility = View.GONE
                            reset_password_editText.visibility = View.VISIBLE
                            reset_confirm_button.setText(R.string.change_password)
                        }
                    }
                    reset_password_editText.isVisible -> {
                        authViewModel.apply {
                            when {
                                passwordValidation(reset_password_editText) -> {
                                    popToLogin(view)
                                }
                            }
                        }
                    }
                    else -> Unit
                }
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
        authViewModel.setNewOTPNumber(sendSMS(number, email))
    }

    private suspend fun sendSMS(number: String, email: String): String = withContext(IO) {
        return@withContext userDataSource.getOTPFromServer(
            number = number,
            email = email
        )
    }
}
