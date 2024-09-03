package com.example.android.chefapp.network.data.response

import com.squareup.moshi.Json

data class BaseResponse<T>(
    @Json(name = "Data")
    val data: T?,
    @Json(name = "Result")
    val result: ResponseResult
)

data class ResponseResult(
    @Json(name = "ErrNo")
    val errNo: Int,
    @Json(name = "ErrMsg")
    val errMsg: String
)