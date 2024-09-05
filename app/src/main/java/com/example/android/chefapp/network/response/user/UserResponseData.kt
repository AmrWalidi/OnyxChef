package com.example.android.chefapp.network.response.user

import com.example.android.chefapp.network.models.user.NetworkUser
import com.squareup.moshi.Json

data class UserResponseData(
    @Json(name = "UserData")
    val userData: NetworkUser
)