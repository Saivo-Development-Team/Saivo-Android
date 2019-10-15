package com.saivo.recommendo.network.access.preference

import com.saivo.recommendo.data.model.domain.Preference
import com.saivo.recommendo.network.resquest.IPreferenceService

class PreferenceDataSource(
    private val preferenceService: IPreferenceService
) : IPreferenceDataSource {
    @Suppress("UNCHECKED_CAST")
    override suspend fun getPreferencesAsync(email: String): ArrayList<Preference> {
        return preferenceService.getPreferencesAsync(email).await().data as ArrayList<Preference>
    }

    override suspend fun getPreferenceAsync(Id: String): Preference? {
        return preferenceService.getPreferenceAsync(Id).await()
            .getObjectFromData(Preference::class.java)
    }

    override suspend fun savePreference(email: String, preference: Preference): String {
        return preferenceService.savePreferenceAsync(email, preference).await().status
    }
}