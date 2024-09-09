package com.example.android.chefapp.repository

import com.example.android.chefapp.database.OnyxChefDatabase
import com.example.android.chefapp.domain.User
import com.example.android.chefapp.network.ApiSuccess
import com.example.android.chefapp.network.OnyxRmsApi
import com.example.android.chefapp.network.handleApi
import com.example.android.chefapp.network.request.ApiRequest
import com.example.android.chefapp.network.request.UserRequestBody
import com.example.android.chefapp.network.response.user.asDatabaseUser
import com.example.android.chefapp.network.response.user.asDomainUser

class LoginRepository(private val database: OnyxChefDatabase) {

    suspend fun login(password: String): User? {
        val response = handleApi {
            OnyxRmsApi.userRetrofitService.getUserDetails(
                ApiRequest(UserRequestBody(password = password))
            )
        }
        val user = when (response) {
            is ApiSuccess -> {
                database.daoUser.insert(response.body.data.user.asDatabaseUser())
                response.body.data.user.asDomainUser()
            }
            else -> null
        }
        return user
    }
}