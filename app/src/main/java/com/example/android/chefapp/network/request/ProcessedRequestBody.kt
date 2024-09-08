package com.example.android.chefapp.network.request

import com.squareup.moshi.Json

data class ProcessedRequestBody(
    @Json(name = "UNT_NO")
    val unitNo: String = Setting.unit.toString(),

    @Json(name = "P_LANG_NO")
    val langNo: String = Setting.language.toString(),

    @Json(name = "P_ITM_SRL")
    val itemSerial: String,

    @Json(name = "P_USR_SRL")
    val orderSerial: String

)