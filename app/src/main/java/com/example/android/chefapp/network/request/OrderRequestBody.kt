package com.example.android.chefapp.network.request

import com.squareup.moshi.Json

data class OrderRequestBody(
    @Json(name = "UNT_NO")
    val unitNo: String = Setting.unit.toString(),

    @Json(name = "P_LANG_NO")
    val langNo: String = Setting.language.toString(),

    @Json(name = "P_BRN_NO")
    val branchNo: String,

    @Json(name = "P_TRMNL_NO")
    val terminalNo: String,
)