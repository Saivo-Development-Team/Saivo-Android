package com.saivo.recommendo.actions

import android.view.View
import androidx.navigation.Navigation
import com.saivo.recommendo.R

object UserAction {
    fun userLoggedIn(view: View){
        Navigation.findNavController(view).navigate(R.id.login_to_main_ui_action)
    }

    fun userRegistered(view: View){
        Navigation.findNavController(view).navigate(R.id.register_to_main_ui_action)
    }
}
