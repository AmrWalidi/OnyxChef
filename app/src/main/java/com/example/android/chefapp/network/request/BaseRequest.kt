package com.example.android.chefapp.network.request

import com.squareup.moshi.Json

data class BaseRequest<T>(@Json(name = "Value") val value: T)