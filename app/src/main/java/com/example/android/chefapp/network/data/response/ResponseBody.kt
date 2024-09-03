package com.example.android.chefapp.network.data.response

import com.squareup.moshi.Json

data class ResponseBody(
    @Json(name = "Data")
    val data: ResponseData,
    @Json(name = "Result")
    val result: ResponseResult
)

interface ResponseData

data class ResponseResult(
    @Json(name = "ErrNo")
    val errNo: Int,
    @Json(name = "ErrMsg")
    val errMsg: String
)