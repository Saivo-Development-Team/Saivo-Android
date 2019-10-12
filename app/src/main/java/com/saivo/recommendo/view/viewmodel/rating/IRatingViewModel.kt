package com.saivo.recommendo.view.viewmodel.rating

import androidx.lifecycle.LiveData
import com.saivo.recommendo.data.model.domain.User
import kotlinx.coroutines.Deferred

interface IRatingViewModel {
    val userData: Deferred<LiveData<User>>
}