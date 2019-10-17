package com.saivo.recommendo.network.access.preference

import com.saivo.recommendo.data.model.domain.Preference
import com.saivo.recommendo.network.resquest.IPreferenceService

class PreferenceDataSource(
    private val preferenceService: IPreferenceService
) : IPreferenceDataSource {
    @Suppress("UNCHECKED_CAST")
    override suspend fun getPreferencesAsync(email: String): ArrayList<Preference> {
        return runCatching {
            preferenceService
                .getPreferencesAsync(email).await()
                .getList<ArrayList<Preference>, Preference>()
        }.getOrDefault(arrayListOf())
    }

    override suspend fun getPreferenceAsync(Id: String): Preference? {
        return runCatching {
            preferenceService
                .getPreferenceAsync(Id).await()
                .getObject<Preference>()
        }.getOrNull()
    }

    override suspend fun savePreference(email: String, preference: Preference): String {
        return runCatching {
            preferenceService.savePreferenceAsync(email, preference).await().getObject<String>()
        }.getOrDefault("")
    }
}