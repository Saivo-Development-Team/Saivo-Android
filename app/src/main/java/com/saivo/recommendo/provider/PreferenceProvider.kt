package com.saivo.recommendo.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

abstract class PreferenceProvider(context: Context) {
    private val context = context.applicationContext
    val preferenceSettings: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

}