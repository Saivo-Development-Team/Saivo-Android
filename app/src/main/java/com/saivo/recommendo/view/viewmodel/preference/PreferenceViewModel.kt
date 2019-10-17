package com.saivo.recommendo.view.viewmodel.preference

import androidx.lifecycle.ViewModel
import com.saivo.recommendo.data.model.domain.Preference
import com.saivo.recommendo.data.repository.preference.IPreferenceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class PreferenceViewModel(private val preferenceRepository: IPreferenceRepository) :
    IPreferenceViewModel,
    ViewModel() {

    init {
        CoroutineScope(IO).launch {
            preferenceRepository.getPreferences().forEach {
                addPreference(it)
            }
        }
    }

    override suspend fun getPreference(): ArrayList<Preference> {
        return preferenceRepository.getPreferences() as ArrayList<Preference>
    }

    override suspend fun addPreference(preference: Preference) {
        preferenceRepository.savePreference(preference)
    }
}