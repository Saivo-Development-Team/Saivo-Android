package com.saivo.recommendo.view.viewable.user

import androidx.lifecycle.ViewModel
import com.saivo.recommendo.data.repository.user.IUserRepository
import com.saivo.recommendo.util.helpers.lazyLoad

class UserViewModel(
    private val repository: IUserRepository
): ViewModel() {
    val userData by lazyLoad {
        repository.getUserData()
    }
}