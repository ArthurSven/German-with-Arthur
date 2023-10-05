package com.devapps.germanwitharthur.ViewModels

import androidx.lifecycle.ViewModel
import com.devapps.germanwitharthur.Data.Repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

suspend fun userSignUp(
    username: String, email: String, password: String) : Boolean {
    return try {
        userRepository.userSignUp(username, email, password)
        true
    } catch (e: Exception) {
        false
    }
}

    suspend fun userLogin(email: String, password: String) {
        return withContext(Dispatchers.IO) {
            userRepository.userLogin(email, password)
        }
    }

    suspend fun resetPassword(email: String) {
        return withContext(Dispatchers.IO) {
            userRepository.resetPassword(email)
        }
    }
    }
