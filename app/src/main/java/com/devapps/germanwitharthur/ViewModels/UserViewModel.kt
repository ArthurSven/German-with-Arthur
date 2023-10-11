package com.devapps.germanwitharthur.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devapps.germanwitharthur.Data.Repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean>
        get() = _isLoggedIn


suspend fun userSignUp(
    username: String, email: String, password: String,  onComplete: (Boolean, String?) -> Unit) {
        try {
            userRepository.userSignUp(username, email, password) { success, errorMessages ->
                if (success) {
                    onComplete(true, null)
                }
            }
        } catch (e: Exception) {
                onComplete(false, null)
        }
}

    suspend fun userLogin(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
         try {
            userRepository.userLogin(email, password) {succeed ->
                if (succeed) {
                    _isLoggedIn.postValue(true)
                    onComplete(true, null)

                    return@userLogin
                }
            }

        } catch (e: Exception) {
            _isLoggedIn.postValue(false)
             onComplete(false, null)
        }
    }

    suspend fun resetPassword(email: String) {
        return withContext(Dispatchers.IO) {
            userRepository.resetPassword(email)
        }
    }
    }
