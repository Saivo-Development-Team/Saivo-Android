package com.saivo.recommendo.view.viewmodel.preference

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.saivo.recommendo.data.model.domain.User
import com.saivo.recommendo.data.repository.user.IUserRepository
import com.saivo.recommendo.util.helpers.lazyLoad
import kotlinx.coroutines.Deferred

class PreferenceViewModel(
    private val repository: IUserRepository
) : IPreferenceViewModel, ViewModel() {
    override val userData: Deferred<LiveData<User>> by lazyLoad {
        repository.getUserData()
    }
}