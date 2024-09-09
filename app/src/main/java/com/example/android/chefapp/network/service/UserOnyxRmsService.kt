package com.example.android.chefapp.network.service

import com.example.android.chefapp.network.request.ApiRequest
import com.example.android.chefapp.network.request.UserRequestBody
import com.example.android.chefapp.network.response.ApiResponse
import com.example.android.chefapp.network.response.user.ApiUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserOnyxRmsService {

    @POST("User/GetUserDetails")
    suspend fun getUserDetails(@Body post: ApiRequest<UserRequestBody>): Response<ApiResponse<ApiUser>>
}