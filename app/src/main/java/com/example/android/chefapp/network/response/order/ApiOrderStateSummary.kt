package com.example.android.chefapp.network.response.order

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
    val stateList: MutableList<SummaryOrderState> = mutableListOf()
    orderStates?.first().let { states ->
        if (states != null) {

            if (states.allCount != "") {
                stateList.add(SummaryOrderState(states.allLabel, states.allCount.toInt()))
            } else {
                stateList.add(SummaryOrderState(states.allLabel, 0))
            }
            if (states.canceledCount != "") {
                stateList.add(
                    SummaryOrderState(
                        states.canceledLabel,
                        states.canceledCount.toInt()
                    ),
                )
            } else {
                stateList.add(SummaryOrderState(states.canceledLabel, 0))
            }

            if (states.delayedCount != "") {
                stateList.add(
                    SummaryOrderState(
                        states.delayedLabel,
                        states.delayedCount.toInt()
                    )
                )
            } else {
                stateList.add(SummaryOrderState(states.delayedLabel, 0))
            }
        }
    }
    return stateList
}
