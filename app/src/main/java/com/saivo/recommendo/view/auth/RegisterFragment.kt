package com.saivo.recommendo.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saivo.recommendo.R
import com.saivo.recommendo.actions.UserAction
import com.saivo.recommendo.data.objects.RegisterCredentials
import com.saivo.recommendo.network.access.DataSource
import com.saivo.recommendo.util.helpers.Display
import com.saivo.recommendo.view.main.CoroutineFragment
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class RegisterFragment : CoroutineFragment(), KodeinAware {
    override val kodein by closestKodein()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataSource: DataSource by instance()

        register_register_button.setOnClickListener {
            val credentials = RegisterCredentials(
                email = register_email_editText.text.toString(),
                password = register_password_editText.text.toString(),
                lastname = register_lastname_editText.text.toString(),
                firstname = register_firstname_editText.text.toString()
            )
            credentials.run {
                when{
                    email.isEmpty() ->  Display.toastMessage(this@RegisterFragment.context, "Email is Empty!", 1)
                    password.isEmpty() ->  Display.toastMessage(this@RegisterFragment.context, "Password is Empty!", 1)
                    firstname.isEmpty() ->  Display.toastMessage(this@RegisterFragment.context, "Firstname is Empty!", 1)
                    lastname.isEmpty() ->  Display.toastMessage(this@RegisterFragment.context, "Lastname is Empty!", 1)
                    else -> launch {
                        val r = with(dataSource) { registerUserAsync(credentials) }
                        if(r != null) {
                            when{
                                r.status == "REGISTRATION_SUCCESSFUL" -> {
                                    Display.toastMessage(this@RegisterFragment.context, "Welcome")
                                    UserAction.userRegistered(view)
                                }
                            }
                        } else {
                            Display.toastMessage(this@RegisterFragment.context, "Server Error")
                        }
                    }
                }
            }
        }
    }

}
