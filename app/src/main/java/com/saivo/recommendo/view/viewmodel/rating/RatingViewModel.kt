package com.saivo.recommendo.view.viewmodel.rating

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.saivo.recommendo.data.model.domain.User
import com.saivo.recommendo.data.repository.user.IUserRepository
import com.saivo.recommendo.util.helpers.lazyLoad
import kotlinx.coroutines.Deferred

class RatingViewModel(
    private val repository: IUserRepository
) : IRatingViewModel, ViewModel() {
    override val userData: Deferred<LiveData<User>> by lazyLoad {
        repository.getUserData()
    }
}