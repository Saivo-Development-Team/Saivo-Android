package com.saivo.recommendo.ui.auth


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import at.favre.lib.crypto.bcrypt.BCrypt
import at.favre.lib.crypto.bcrypt.BCryptParser
import com.saivo.recommendo.R
import com.saivo.recommendo.data.model.User
import com.saivo.recommendo.network.ApiService
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
        val userService = ApiService()
        var users: List<User>



        login_register_button.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.login_to_register_action)
        }
        login_login_button.setOnClickListener {
            var user: User? = null
            CoroutineScope(context = Dispatchers.IO).launch {
                users = userService.getUsersAsync().await()
                println("Debug: $users\n\n")
               user = users.find {
                    user -> user.username == login_username_editText.text.toString() &&
                        BCrypt.verifyer().verify(
                            login_password_editText.text.toString().toByteArray(),
                            user.password.toByteArray()
                        ).verified
               }
                if (user == null) {
                    login_username_editText.setBackgroundColor(Color.RED)
                    login_password_editText.setBackgroundColor(Color.RED)
                } else {
                    Navigation.findNavController(it).navigate(R.id.login_to_main_ui_action)
                }
            }
        }
        login_reset_password_button.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.login_to_reset_password_action)
        }
    }
}
