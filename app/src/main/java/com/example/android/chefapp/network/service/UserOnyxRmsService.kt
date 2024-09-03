package com.example.android.chefapp.network.service

import com.example.android.chefapp.network.data.request.RequestBody
import com.example.android.chefapp.network.data.request.user.UserRequestValue
import com.example.android.chefapp.network.data.response.user.UserResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserOnyxRmsService {

    @POST("User/GetUserDetails")
    fun getUserDetails(@Body post: RequestBody<UserRequestValue>) : Call<UserResponseData>
}