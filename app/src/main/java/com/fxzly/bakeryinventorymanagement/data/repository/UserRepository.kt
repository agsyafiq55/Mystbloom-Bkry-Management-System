package com.fxzly.bakeryinventorymanagement.data.repository

import androidx.lifecycle.LiveData
import com.fxzly.bakeryinventorymanagement.data.dao.UserDao
import com.fxzly.bakeryinventorymanagement.data.datamodel.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class UserRepository(private val usersDao: UserDao) {
    val readAllData: LiveData<List<User>> = usersDao.readAllData()

    suspend fun isUsernameExists(username: String): Boolean {
        return usersDao.isUsernameExists(username) != null
    }

    suspend fun registerUser(user: User){
        usersDao.addUser(user)
    }
    suspend fun loginUser(username: String, password: String): User? {
        return usersDao.loginUser(username, password)
    }

    fun getUser(userId: Int): Flow<User> {
        return usersDao.getUserById(userId)
    }

    suspend fun updateUser(user: User) {
        usersDao.updateUser(user)
    }
    suspend fun updateUserImage(userId: Int, imageUri: String) {
        usersDao.updateUserImage(userId, imageUri)
    }
}
