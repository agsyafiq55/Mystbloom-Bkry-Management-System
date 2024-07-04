package com.fxzly.bakeryinventorymanagement.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fxzly.bakeryinventorymanagement.data.datamodel.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM users ORDER BY UserID ASC")
    fun readAllData(): LiveData<List<User>>

    // For Login: Check if a user with the given username and password exists
    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    fun getUserForLogin(username: String, password: String): Flow<User>

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun isUsernameExists(username: String): User?

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun loginUser(username: String, password: String): User?

    @Query("SELECT * FROM users WHERE userID = :userId")
    fun getUserById(userId: Int): Flow<User>

    @Query("UPDATE users SET user_img = :imageUri WHERE userID = :userID")
    suspend fun updateUserImage(userID: Int, imageUri: String)
}