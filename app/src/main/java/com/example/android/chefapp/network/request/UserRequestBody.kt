package com.example.android.chefapp.network.request


import com.squareup.moshi.Json

data class UserRequestBody(
    @Json(name = "UNT_NO")
    val unitNo: String = Setting.unit.toString(),

    @Json(name = "P_LANG_NO")
    val langNo: String = Setting.language.toString(),

//    @Json(name = "P_HND_DVC_SRL")
//    val deviceNo: String = Build.getSerial() ?: "",

    @Json(name = "P_PASSWORD")
    val password: String
)