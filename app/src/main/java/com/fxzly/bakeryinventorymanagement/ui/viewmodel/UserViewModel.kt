package com.fxzly.bakeryinventorymanagement.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.fxzly.bakeryinventorymanagement.data.datamodel.User
import com.fxzly.bakeryinventorymanagement.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(
    application: Application,
    private val userRepository: UserRepository
) : AndroidViewModel(application) {

    val readAllData: LiveData<List<User>> = userRepository.readAllData

    // Method to check if the username exists
    fun isUsernameExists(username: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val userExists = userRepository.isUsernameExists(username)
            withContext(Dispatchers.Main) {
                onResult(userExists)
            }
        }
    }

    fun registerUser(user: User, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val userExists = userRepository.isUsernameExists(user.username)
            if (userExists) {
                withContext(Dispatchers.Main) {
                    onResult(false, "Username already exists")
                }
            } else {
                userRepository.registerUser(user)
                withContext(Dispatchers.Main) {
                    onResult(true, "Successfully registered")
                }
            }
        }
    }

    fun loginUser(username: String, password: String, onLoginResult: (success: Boolean, errorMessage: String?, user: User?) -> Unit) {
        viewModelScope.launch {
            val user = userRepository.loginUser(username, password)
            if (user != null) {
                // Successful login
                onLoginResult(true, null, user)
            } else {
                // Login failed
                onLoginResult(false,"Invalid username or password", null)
            }
        }
    }
}