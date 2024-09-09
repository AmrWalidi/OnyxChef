package com.example.android.chefapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.android.chefapp.database.getDatabase
import com.example.android.chefapp.domain.User
import com.example.android.chefapp.repository.UserRepository
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val repo = UserRepository(database)

    val user = MutableLiveData<User>()


    init {
        viewModelScope.launch {
            user.value = repo.getCurrentUser()
        }
    }

    fun refreshOrders() {
        viewModelScope.launch {
            user.value?.let { repo.refreshOrders(it) }
        }
    }



    fun logout() {
        viewModelScope.launch {
            user.value?.let { repo.logout(it) }
        }
    }



    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}