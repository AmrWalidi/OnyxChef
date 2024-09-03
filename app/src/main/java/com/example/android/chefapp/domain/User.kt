package com.example.android.chefapp.domain

import com.squareup.moshi.Json

class User(
    @Json(name = "USR_NM")
    val userName: String,

    @Json(name = "LOGIN")
    private val loginData: String
) {

    val getDate = loginData.split(' ')[0].replace('/', '-')
}