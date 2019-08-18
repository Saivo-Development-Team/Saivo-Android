package com.saivo.recommendo.data.repository

import androidx.lifecycle.LiveData
import com.saivo.recommendo.data.access.UserDao
import com.saivo.recommendo.data.model.domain.User
import com.saivo.recommendo.data.model.infrastructure.UserData
import com.saivo.recommendo.network.access.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val dataSource: DataSource
) : UserRepository {

    init {
        dataSource.userData.observeForever {
            userData -> run {
                storeUserData(userData)
            }
        }
    }

    override suspend fun getUserData(): LiveData<User> {
        return withContext(Dispatchers.IO) {
            onStartup()
            return@withContext userDao.getUserData("33d43e0c-3c53-4cac-addb-3245b53fe54a")
        }
    }

    private fun storeUserData(userData: UserData){
            userDao.updateUserData(userData.getUserDetailsFromData())
    }

    private fun onStartup(){
        if (updatedDataNeeded(ZonedDateTime.now().minusHours(1))){
                fetchUserData()
        }
    }

    private fun fetchUserData(){
        dataSource.userData.value
    }

    private fun updatedDataNeeded(lastUpdateTime: ZonedDateTime): Boolean {
        return lastUpdateTime.isBefore(ZonedDateTime.now().minusMinutes(15))
    }

}
