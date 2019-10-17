package com.saivo.recommendo.view.viewmodel.preference

import com.saivo.recommendo.data.model.domain.Preference

interface IPreferenceViewModel {
    suspend fun getPreference(): ArrayList<Preference>
    suspend fun addPreference(preference: Preference)
}