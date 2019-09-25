package com.saivo.recommendo.view.viewmodel.user

import androidx.lifecycle.LiveData
import com.saivo.recommendo.data.model.domain.User
import kotlinx.coroutines.Deferred

interface IUserViewModel {
    val userData: Deferred<LiveData<User>>
}