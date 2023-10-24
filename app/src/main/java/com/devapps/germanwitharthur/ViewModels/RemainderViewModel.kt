package com.devapps.germanwitharthur.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devapps.germanwitharthur.Data.Dao.RemainderDao
import com.devapps.germanwitharthur.Data.Model.Remainder
import com.devapps.germanwitharthur.Data.Repository.RemainderRepository
import kotlinx.coroutines.launch

class RemainderViewModel(val remainderRepository: RemainderRepository) : ViewModel() {

    private fun upsertRemainder(remainder: Remainder) {
        viewModelScope.launch {
            remainderRepository.upsertRemainder(remainder)
        }
    }


}

class RemainderViewModelFactory(private val remainderRepository: RemainderRepository) :
        ViewModelProvider.Factory {
     override fun <T : ViewModel> create(modelClass: Class<T>): T {
         if (modelClass.isAssignableFrom(RemainderViewModel::class.java)) {
             @Suppress("UNCHECKED_CAST")
             return RemainderViewModel(remainderRepository) as T
         }
         throw IllegalArgumentException("Unknown ViewModel class")
    }
        }