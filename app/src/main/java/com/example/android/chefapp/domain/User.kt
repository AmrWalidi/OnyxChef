package com.example.android.chefapp.domain

import com.squareup.moshi.Json

data class User(
    @Json(name = "USR_NM")
    val userName : String
)