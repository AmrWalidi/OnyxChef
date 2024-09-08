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

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    val database = getDatabase(application)
    private val repo = LoginRepository(database)

    private val _password = MutableLiveData("")
    val password: LiveData<String>
        get() = _password

    private val _user = MutableLiveData<User>()
    val user : LiveData<User>
        get() = _user


    fun pinOnClick(number: Int) {
        _password.value += number.toString()
    }

    fun login() {
        viewModelScope.launch {
            _user.value = _password.value?.let { repo.login(it) }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}