package com.example.android.chefapp.network.data.request.user

import android.os.Build
import com.example.android.chefapp.network.data.Setting
import com.squareup.moshi.Json

data class UserRequestValue(
    @Json(name = "UNT_NO")
    val unitNo: String = Setting.unitNo,

    @Json(name = "P_LANG_NO")
    val langNo: String = Setting.languageNo,

//    @Json(name = "P_HND_DVC_SRL")
//    val deviceNo: String = Build.getSerial() ?: "",

    @Json(name = "P_PASSWORD")
    val password: String
)