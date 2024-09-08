package com.example.android.chefapp.network.response

import com.squareup.moshi.Json

data class ApiResponse<T>(
    @Json(name = "Data")
    val data: T,
    @Json(name = "Result")
    val result: ApiResult
)

data class ApiResult(
    @Json(name = "ErrNo")
    val errNo: Int,
    @Json(name = "ErrMsg")
    val errMsg: String
)