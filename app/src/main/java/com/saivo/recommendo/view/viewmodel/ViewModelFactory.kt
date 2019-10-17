package com.saivo.recommendo.view.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saivo.recommendo.data.access.ClientDao
import com.saivo.recommendo.data.access.TokenDao
import com.saivo.recommendo.data.repository.preference.IPreferenceRepository
import com.saivo.recommendo.data.repository.user.IUserRepository
import com.saivo.recommendo.util.network.IConnection
import com.saivo.recommendo.view.viewmodel.auth.AuthViewModel
import com.saivo.recommendo.view.viewmodel.auth.ServerViewModel
import com.saivo.recommendo.view.viewmodel.preference.PreferenceViewModel
import com.saivo.recommendo.view.viewmodel.user.UserViewModel

class ViewModelFactory(
    private val context: Context,
    private val tokenDao: TokenDao,
    private val clientDao: ClientDao,
    private val connection: IConnection,
    private val userRepository: IUserRepository,
    private val preferenceRepository: IPreferenceRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            checkType<MainViewModel>(modelClass) -> MainViewModel(context) as T
            checkType<PreferenceViewModel>(modelClass) -> PreferenceViewModel(preferenceRepository) as T
            checkType<AuthViewModel>(modelClass) -> AuthViewModel() as T
            checkType<UserViewModel>(modelClass) -> UserViewModel(userRepository) as T
            checkType<ServerViewModel>(modelClass) -> ServerViewModel(
                tokenDao,
                clientDao,
                connection
            ) as T
            else -> throw Exception("This is not a ViewModel")
        }
    }

    private inline fun <reified I : ViewModel> checkType(modelClass: Class<*>): Boolean {
        return modelClass.isAssignableFrom(I::class.java)
    }

}