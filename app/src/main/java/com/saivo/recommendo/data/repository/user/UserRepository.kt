package com.saivo.recommendo.data.repository.user

import androidx.lifecycle.LiveData
import com.saivo.recommendo.data.access.UserDao
import com.saivo.recommendo.data.model.domain.User
import com.saivo.recommendo.data.model.infrastructure.UserData
import com.saivo.recommendo.network.access.user.IUserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class UserRepository(
    private val userDao: UserDao,
    private val userDataSource: IUserDataSource
) : IUserRepository {

    init {
        userDataSource.userData.observeForever { userData ->
            run {
                GlobalScope.launch(Dispatchers.IO) {
                    storeUserData(userData)
                }
            }
        }
    }

    override suspend fun getUserData(): LiveData<User> {
        return withContext(Dispatchers.IO) {
            onStartup()
            userDao.getUserData()
        }
    }

    private suspend fun storeUserData(userData: UserData) {
        withContext(Dispatchers.IO) {
            userDao.updateUserData(userData.getUserDetailsFromData())
        }
    }

    private fun onStartup() {
        if (updatedDataNeeded(ZonedDateTime.now().minusHours(1))) {
            fetchUserData()
        }
    }

    private fun fetchUserData() {
        userDataSource.userData.value
    }

    private fun updatedDataNeeded(lastUpdateTime: ZonedDateTime): Boolean {
        return lastUpdateTime.isBefore(ZonedDateTime.now().minusMinutes(15))
    }

}
