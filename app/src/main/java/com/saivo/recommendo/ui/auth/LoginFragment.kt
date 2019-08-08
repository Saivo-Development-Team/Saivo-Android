package com.saivo.recommendo.ui.auth


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.saivo.recommendo.R
import com.saivo.recommendo.actions.UserAction
import com.saivo.recommendo.data.model.domain.User
import com.saivo.recommendo.data.objects.Login
import com.saivo.recommendo.network.access.DataSource
import com.saivo.recommendo.util.helpers.Display
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var user: User? = null
        val dataSource = DataSource(this.context!!)

        login_login_button.setOnClickListener {
            // TODO - Abstract all the code
            //      - Use helper functions
            val login = Login(
                password = login_password_editText.text.toString(),
               username = login_username_editText.text.toString()
            )
            when {
                // TODO - Better validation is required
                login.username.isEmpty() -> {
                    Display.toastMessage(this@LoginFragment.context, "Username is Empty!", 1)
                }
                login.password.isEmpty() -> {
                    Display.toastMessage(this@LoginFragment.context, "Password is Empty!", 1)
                }
                login.username.isNotEmpty() && login.password.isNotEmpty() -> {
                    GlobalScope.launch(Dispatchers.Main) {
                        Display.toastMessage(this@LoginFragment.context, "Logging You In ")
                        dataSource.loginUserAsync(login).run {
                            dataSource.userData.observe(this@LoginFragment, Observer {
                                user = it
                            })
                            UserAction.login(this@LoginFragment.view!!)
                        }
                    }
                }
            }
        }
    }
}
