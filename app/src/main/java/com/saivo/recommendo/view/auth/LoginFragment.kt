package com.saivo.recommendo.view.auth


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.saivo.recommendo.R
import com.saivo.recommendo.actions.UserAction
import com.saivo.recommendo.data.objects.LoginCredentials
import com.saivo.recommendo.network.access.DataSource
import com.saivo.recommendo.view.main.CoroutineFragment
import com.saivo.recommendo.util.helpers.Display
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class LoginFragment : CoroutineFragment(), KodeinAware {
    override val kodein by closestKodein()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataSource: DataSource by instance()

        login_button.setOnClickListener {
            // TODO - Abstract all the code
            //      - Use helper functions
            val credentials = LoginCredentials(
                password = login_password_editText.text.toString(),
                email = login_email_editText.text.toString()
            )

            when {
                // TODO - Better validation is required
                credentials.email.isEmpty() -> {
                    Display.toastMessage(this@LoginFragment.context, "Username is Empty!", 1)
                }
                credentials.password.isEmpty() -> {
                    Display.toastMessage(this@LoginFragment.context, "Password is Empty!", 1)
                }
                credentials.email.isNotEmpty() && credentials.password.isNotEmpty() -> {
                    launch {
                        val r = with(dataSource) { loginUserAsync(credentials) }
                        if(r != null) {
                            when{
                                r.status == "LOGIN_SUCCESSFUL" -> {
                                    Display.toastMessage(this@LoginFragment.context, "Logging You In ")
                                    UserAction.userLoggedIn(view)
                                }
                                else -> Display.toastMessage(this@LoginFragment.context, "Check your Credentials")
                            }
                        } else {
                            Display.toastMessage(this@LoginFragment.context, "Server Error")
                        }
                    }
                }
            }
        }
        login_register_button.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.login_to_register_action)
        }
        login_reset_password_button.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.login_to_reset_password_action)
        }
    }
}

