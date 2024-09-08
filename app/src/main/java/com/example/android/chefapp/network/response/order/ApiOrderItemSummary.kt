package com.example.android.chefapp.network.response.order

import com.example.android.chefapp.database.entity.DatabaseSummaryItem
import com.example.android.chefapp.domain.SummaryItems
import com.squareup.moshi.Json

data class ApiOrderItemSummary(
    @Json(name = "OrderItemSummryList")
    val items: List<NetworkSummaryItems>
)

data class NetworkSummaryItems(
    @Json(name = "I_CODE") val code: String,
    @Json(name = "I_CODE_NM") val name: String,
    @Json(name = "I_QTY") val quantity: String
)

fun ApiOrderItemSummary.asDomainSummaryItems(): List<SummaryItems> {
    return items.map {
        SummaryItems(
            code = it.code.toInt(),
            name = it.name,
            quantity = it.quantity.toInt()
        )
    }
}

fun ApiOrderItemSummary.asDatabaseSummaryItems(): List<DatabaseSummaryItem> {
    return items.map {
        DatabaseSummaryItem(
            code = it.code.toInt(),
            name = it.name,
            quantity = it.quantity.toInt()
        )
    }
}