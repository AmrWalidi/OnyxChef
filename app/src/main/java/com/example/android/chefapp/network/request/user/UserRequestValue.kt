package com.example.android.chefapp.network.request.user

import com.example.android.chefapp.network.models.Setting
import com.squareup.moshi.Json

data class UserRequestValue(
    @Json(name = "UNT_NO")
    val unitNo: String = Setting.unit,

    @Json(name = "P_LANG_NO")
    val langNo: String = Setting.language,

//    @Json(name = "P_HND_DVC_SRL")
//    val deviceNo: String = Build.getSerial() ?: "",

    @Json(name = "P_PASSWORD")
    val password: String
)