package com.example.android.chefapp.network.response.order

import com.example.android.chefapp.database.entity.DatabaseSummaryOrderState
import com.example.android.chefapp.domain.SummaryOrderState
import com.squareup.moshi.Json

data class ApiOrderStateSummary(
    @Json(name = "OrderStateSummryList")
    val orderStates: List<NetworkOrderState>?
)

data class NetworkOrderState(
    @Json(name = "ALL_ORDRS_LBL") val allLabel: String,
    @Json(name = "ALL_ORDRS_CNT") val allCount: String,
    @Json(name = "CANCELED_ORDRS_LBL") val canceledLabel: String,
    @Json(name = "CANCELED_ORDRS_CNT") val canceledCount: String,
    @Json(name = "DELAYED_ORDRS_LBL") val delayedLabel: String,
    @Json(name = "DELAYED_ORDRS_CNT") val delayedCount: String
)

fun ApiOrderStateSummary.asDomainOrderState(): List<SummaryOrderState> {
    var stateList: List<SummaryOrderState> = listOf()
    orderStates?.first().let { states ->
        if (states != null) {
            stateList = listOf(
                SummaryOrderState(states.allLabel, states.allCount.toInt()),
                SummaryOrderState(states.canceledLabel, states.canceledCount.toInt()),
                SummaryOrderState(states.delayedLabel, states.delayedCount.toInt())
            )
        }
    }
    return stateList
}

fun ApiOrderStateSummary.asDatabaseOrderStateSummary(): Array<DatabaseSummaryOrderState> {
    var stateList: List<DatabaseSummaryOrderState> = listOf()
    orderStates?.first().let { states ->
        if (states != null) {
            stateList = listOf(
                DatabaseSummaryOrderState(states.allLabel, states.allCount.toInt()),
                DatabaseSummaryOrderState(states.canceledLabel, states.canceledCount.toInt()),
                DatabaseSummaryOrderState(states.delayedLabel, states.delayedCount.toInt())
            )
        }
    }
    return stateList.toTypedArray()
}
