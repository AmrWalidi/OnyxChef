package com.example.android.chefapp.repository

import com.example.android.chefapp.database.OnyxChefDatabase
import com.example.android.chefapp.database.entity.asDomainUser
import com.example.android.chefapp.domain.User
import com.example.android.chefapp.network.ApiSuccess
import com.example.android.chefapp.network.OnyxRmsApi
import com.example.android.chefapp.network.handleApi
import com.example.android.chefapp.network.request.ApiRequest
import com.example.android.chefapp.network.request.UserRequestBody
import com.example.android.chefapp.network.response.user.asDatabaseUser
import com.example.android.chefapp.network.response.user.asDomainUser

class LoginRepository(private val database: OnyxChefDatabase) {

    suspend fun getCurrentUser(): User? {
        return database.daoUser.get().asDomainUser()
    }

    suspend fun login(password: String): User? {
        val response = handleApi {
            OnyxRmsApi.userRetrofitService.getUserDetails(
                ApiRequest(UserRequestBody(password = password))
            )
        }
        val user = when (response) {
            is ApiSuccess -> {
                response.body.data.user?.asDatabaseUser()?.let { database.daoUser.insert(it) }
                response.body.data.user?.asDomainUser()
            }
            else -> null
        }
        return user
    }

    suspend fun logout(user: User) {
        return database.daoUser.delete(user.unit)
    }
}