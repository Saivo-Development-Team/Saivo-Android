package com.saivo.saivo_recommendo.ui.auth


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.saivo.saivo_recommendo.R
import kotlinx.android.synthetic.main.fragment_login.*


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
        login_register_button.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.to_register_action)
        }
        login_login_button.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.login_to_main_action)
        }
    }
}
