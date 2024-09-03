package com.example.android.chefapp.network.data.response.user

import com.example.android.chefapp.domain.User
import com.example.android.chefapp.network.data.response.ResponseData
import com.squareup.moshi.Json

data class UserResponseData(
    @Json(name = "UserData")
    val userData : User
) : ResponseData