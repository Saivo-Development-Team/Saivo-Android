package com.saivo.recommendo.data.repository.preference

import com.saivo.recommendo.data.access.PreferenceDao
import com.saivo.recommendo.data.access.UserDao
import com.saivo.recommendo.data.model.domain.Preference
import com.saivo.recommendo.network.access.preference.IPreferenceDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PreferenceRepository(
    private val userDao: UserDao,
    private val preferenceDao: PreferenceDao,
    private val preferenceDataSource: IPreferenceDataSource
) : IPreferenceRepository {

    private lateinit var preference: Preference

    override suspend fun getPreferences(): List<Preference> {
        initData()
        return preferenceDao.getPreferences()
    }

    override suspend fun getPreference(Id: String): Preference {
        return preferenceDao.getPreference(Id) ?: preference
    }

    override suspend fun savePreference(preference: Preference) {
        preferenceDataSource.savePreference(userDao.getUserEmail(), preference)
        preferenceDao.savePreference(preference)
    }

    override suspend fun savePreferences(preferences: List<Preference>) {
        preferenceDao.savePreferences(preferences)
    }

    private fun initData() = CoroutineScope(IO).launch {
        val email = withContext(IO) {
            userDao.getUserEmail()
        }

        preferenceDataSource.getPreferencesAsync(email).let { preferences ->
            preferences.forEach {
                println(it)
                preferenceDao.savePreference(it)
            }
        }

    }
}