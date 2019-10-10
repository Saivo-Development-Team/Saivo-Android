package com.saivo.recommendo.view.fragment.dialog

import com.saivo.recommendo.view.objects.preferences.PreCard

interface IPreferenceDialogListener {
    fun onPreferenceSaved(card: PreCard)
}