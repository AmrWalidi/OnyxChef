package com.example.android.chefapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.android.chefapp.database.getDatabase
import com.example.android.chefapp.domain.User
import com.example.android.chefapp.repository.LoginRepository
import kotlinx.coroutines.launch


class HomeViewModel(application: Application, u: User) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val repo = LoginRepository(database)

    private val _user = MutableLiveData(u)
    val user: LiveData<User>
        get() = _user

    private val _fragmentNumber = MutableLiveData(1)
    val fragmentNumber: LiveData<Int>
        get() = _fragmentNumber


    fun navigateThroughFragments(fragment: Int) {
        _fragmentNumber.value = fragment
    }


    fun logout() {
        viewModelScope.launch {
            _user.value?.let { repo.logout(it) }
        }
    }


    class Factory(val app: Application, val user: User) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(app, user) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}