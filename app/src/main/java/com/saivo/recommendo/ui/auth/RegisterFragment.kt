package com.saivo.recommendo.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.saivo.recommendo.R
import com.saivo.recommendo.data.model.User
import com.saivo.recommendo.network.ApiService
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userService = ApiService()

        login_register_button.setOnClickListener {
            val user = User(
                username = username_editText.text.toString(),
                lastname = lastname_editText.text.toString(),
                firstname = firstname_editText.text.toString(),
                email = login_username_editText.text.toString(),
                password = login_password_editText.text.toString()
            )

            GlobalScope.launch(Dispatchers.Main) {
                val text = userService.registerUserAsync(user).await()
                main_textview.text = text
            }

            Navigation.findNavController(it).navigate(R.id.register_to_main_ui_action)
        }
    }

}
