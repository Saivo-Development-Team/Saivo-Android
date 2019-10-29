package com.saivo.recommendo.view.fragment.dialog

import com.saivo.recommendo.view.objects.preferences.PreferenceCard

interface IPreferenceDialogListener {
    fun onPreferenceSaved(card: PreferenceCard)
}