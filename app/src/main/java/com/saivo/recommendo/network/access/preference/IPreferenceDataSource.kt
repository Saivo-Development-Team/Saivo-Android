package com.saivo.recommendo.network.access.preference

import com.saivo.recommendo.data.model.domain.Preference

interface IPreferenceDataSource {
    suspend fun getPreferenceAsync(Id: String) : Preference?
    suspend fun getPreferencesAsync(email: String) : ArrayList<Preference>
    suspend fun savePreference(email: String, preference: Preference): String
}