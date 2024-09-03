package com.example.android.chefapp.network.service

import com.example.android.chefapp.network.data.request.BaseRequest
import com.example.android.chefapp.network.data.request.user.UserRequestValue
import com.example.android.chefapp.network.data.response.BaseResponse
import com.example.android.chefapp.network.data.response.user.UserResponseData
import retrofit2.http.Body
import retrofit2.http.POST

interface UserOnyxRmsService {

    @POST("User/GetUserDetails")
    suspend fun getUserDetails(@Body post: BaseRequest<UserRequestValue>) : BaseResponse<UserResponseData>
}