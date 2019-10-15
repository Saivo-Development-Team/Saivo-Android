package com.saivo.recommendo.data.repository.preference

import com.saivo.recommendo.data.model.domain.Preference

interface IPreferenceRepository {
    suspend fun getPreference(Id: String) : Preference
    suspend fun getPreferences() : List<Preference>
    suspend fun savePreference(preference: Preference)
    suspend fun savePreferences(preferences: List<Preference>)
}