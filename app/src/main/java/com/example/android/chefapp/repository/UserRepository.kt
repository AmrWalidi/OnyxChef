package com.example.android.chefapp.repository


import com.example.android.chefapp.database.OnyxChefDatabase
import com.example.android.chefapp.database.entity.asDomainUser
import com.example.android.chefapp.domain.user.User
import com.example.android.chefapp.network.OnyxRmsApi
import com.example.android.chefapp.network.models.user.asDatabaseUser
import com.example.android.chefapp.network.models.user.asDomainUser
import com.example.android.chefapp.network.request.BaseRequest
import com.example.android.chefapp.network.request.user.UserRequestValue
import com.example.android.chefapp.network.response.BaseResponse
import com.example.android.chefapp.network.response.user.UserResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val database: OnyxChefDatabase) {

    suspend fun getCurrentUser () : User? {
            return database.userDao.getUser()?.asDomainUser()
    }

    suspend fun login(password: String): User? {
        val response: BaseResponse<UserResponseData>
        withContext(Dispatchers.IO) {
            response = OnyxRmsApi.userRetrofitService
                .getUserDetails(BaseRequest(UserRequestValue(password = password)))

        }
        val user = when (response.result.errNo) {
            0 -> {
                database.userDao.addUser(response.data.userData.asDatabaseUser())
                response.data.userData.asDomainUser()
            }

            else -> null
        }
        return user
    }

}