package com.saivo.recommendo.view.viewmodel

import androidx.lifecycle.ViewModel
import com.saivo.recommendo.data.repository.UserRepository
import com.saivo.recommendo.util.helpers.lazyLoad

class UserViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    val userData by lazyLoad {
        userRepository.getUserData()
    }
}