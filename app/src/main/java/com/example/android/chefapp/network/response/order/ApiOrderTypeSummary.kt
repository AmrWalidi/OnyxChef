package com.example.android.chefapp.network.response.order

import com.example.android.chefapp.domain.SummaryOrderType
import com.squareup.moshi.Json

data class ApiOrderTypeSummary(
    @Json(name = "OrderTypeSummryList")
    val orderTypes: List<NetworkOrderType>?
)

data class NetworkOrderType(
    @Json(name = "BILL_DOC_TYPE") val type: String,
    @Json(name = "BILL_DOC_TYPE_NM") val name: String,
    @Json(name = "BILL_TYP_CNT") val count: String,
)

fun ApiOrderTypeSummary.asDomainOrderType(): List<SummaryOrderType> {
    return orderTypes?.map {
        SummaryOrderType(
            name = it.name,
            count = it.count.toInt()
        )
    } ?: listOf()
}