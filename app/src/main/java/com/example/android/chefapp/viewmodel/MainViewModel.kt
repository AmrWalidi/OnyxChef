package com.example.android.chefapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.chefapp.domain.User
import com.example.android.chefapp.network.OnyxRmsApi
import com.example.android.chefapp.network.data.request.BaseRequest
import com.example.android.chefapp.network.data.request.user.UserRequestValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel : ViewModel() {

    private val _user = MutableLiveData<User>()

    val user: LiveData<User>
        get() = _user

    init {
        viewModelScope.launch(Dispatchers.Default) {
            getUserDetail()
        }
    }

    private suspend fun getUserDetail() {

        val response = OnyxRmsApi
            .userRetrofitService
            .getUserDetails(BaseRequest(UserRequestValue(password = "123456")))


        withContext(Dispatchers.Main) {

            _user.value = response.data?.userData
        }
    }

}