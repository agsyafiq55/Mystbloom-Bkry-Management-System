package com.fxzly.bakeryinventorymanagement.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fxzly.bakeryinventorymanagement.data.datamodel.User
import com.fxzly.bakeryinventorymanagement.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null) // Use nullable User to handle initial state
    val user: StateFlow<User?> get() = _user

    fun fetchUser(userId: Int) {
        viewModelScope.launch {
            userRepository.getUser(userId).collect { user ->
                _user.value = user
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            userRepository.updateUser(user)
        }
    }
    fun updateUserImage(userId: Int, imageUri: String) {
        viewModelScope.launch {
            val user = userRepository.getUser(userId).firstOrNull()
            if (user != null) {
                val updatedUser = user.copy(user_img = imageUri)
                userRepository.updateUser(updatedUser)
                _user.value = updatedUser
            }
        }
    }
}
