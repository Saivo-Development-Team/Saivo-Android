package com.saivo.recommendo.view.viewmodel.preference

import androidx.lifecycle.LiveData
import com.saivo.recommendo.data.model.domain.User
import kotlinx.coroutines.Deferred

interface IPreferenceViewModel {
    val userData: Deferred<LiveData<User>>
}