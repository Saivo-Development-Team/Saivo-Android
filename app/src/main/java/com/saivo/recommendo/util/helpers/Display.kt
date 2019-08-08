package com.saivo.recommendo.util.helpers

import android.content.Context
import android.widget.Toast

object Display {

    fun toastMessage(context: Context?, message: String = "", duration: Int = 0){
        Toast.makeText(context, message, duration).show()
    }

}