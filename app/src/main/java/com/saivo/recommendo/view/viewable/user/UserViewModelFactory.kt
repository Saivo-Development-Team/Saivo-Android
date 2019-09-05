package com.saivo.recommendo.view.viewable.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saivo.recommendo.data.repository.user.IUserRepository

class UserViewModelFactory(
    private val repository: IUserRepository
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(repository) as T
    }
}