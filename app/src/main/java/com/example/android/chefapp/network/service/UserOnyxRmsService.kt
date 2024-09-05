package com.example.android.chefapp.network.service

import com.example.android.chefapp.network.request.BaseRequest
import com.example.android.chefapp.network.request.user.UserRequestValue
import com.example.android.chefapp.network.response.BaseResponse
import com.example.android.chefapp.network.response.user.UserResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserOnyxRmsService {

    @POST("User/GetUserDetails")
    suspend fun getUserDetails(@Body post: BaseRequest<UserRequestValue>) : BaseResponse<UserResponseData>
}