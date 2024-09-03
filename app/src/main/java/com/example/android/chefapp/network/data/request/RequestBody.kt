package com.example.android.chefapp.network.data.request

import com.squareup.moshi.Json

data class RequestBody<T>(@Json(name = "Value") val value: T)