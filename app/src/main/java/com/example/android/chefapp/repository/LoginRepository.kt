package com.example.android.chefapp.repository

import com.example.android.chefapp.database.OnyxChefDatabase
import com.example.android.chefapp.database.entity.asDomainUser
import com.example.android.chefapp.domain.User
import com.example.android.chefapp.network.OnyxRmsApi
import com.example.android.chefapp.network.request.ApiRequest
import com.example.android.chefapp.network.request.UserRequestBody
import com.example.android.chefapp.network.response.ApiResponse
import com.example.android.chefapp.network.response.user.ApiUser
import com.example.android.chefapp.network.response.user.asDatabaseUser
import com.example.android.chefapp.network.response.user.asDomainUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(private val database: OnyxChefDatabase) {
    suspend fun getCurrentUser(): User? {
        return database.daoUser.get().asDomainUser()
    }

    suspend fun login(password: String): User? {
        val response: ApiResponse<ApiUser>
        withContext(Dispatchers.IO) {
            response = OnyxRmsApi.userRetrofitService
                .getUserDetails(ApiRequest(UserRequestBody(password = password)))

        }
        val user = when (response.result.errNo) {
            0 -> {
                database.daoUser.insert(response.data.user.asDatabaseUser())
                response.data.user.asDomainUser()
            }

            else -> null
        }
        return user
    }
}