package com.example.android.chefapp.network.request

import com.squareup.moshi.Json

data class ApiRequest<T>(@Json(name = "Value") val value: T)
