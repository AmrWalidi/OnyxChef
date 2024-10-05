package com.example.android.chefapp.repository

import com.example.android.chefapp.database.OnyxChefDatabase
import com.example.android.chefapp.domain.SummaryItems
import com.example.android.chefapp.domain.SummaryOrderState
import com.example.android.chefapp.domain.SummaryOrderType
import com.example.android.chefapp.network.ApiSuccess
import com.example.android.chefapp.network.OnyxRmsApi
import com.example.android.chefapp.network.handleApi
import com.example.android.chefapp.network.request.ApiRequest
import com.example.android.chefapp.network.request.OrderRequestBody
import com.example.android.chefapp.network.response.order.asDomainOrderState
import com.example.android.chefapp.network.response.order.asDomainOrderType
import com.example.android.chefapp.network.response.order.asDomainSummaryItems

class SummaryRepository(val database: OnyxChefDatabase) {

    suspend fun getOrderType(terminal: Int?, branch: Int?): List<SummaryOrderType> {
        val orderTypeResponse = handleApi {
            OnyxRmsApi.orderRetrofitService.getOrderTypeSummary(
                ApiRequest(
                    OrderRequestBody(
                        terminalNo = terminal.toString(),
                        branchNo = branch.toString()
                    )
                )
            )
        }
        if (orderTypeResponse is ApiSuccess) {
            return orderTypeResponse.body.data.asDomainOrderType()
        }
        return listOf()
    }

    suspend fun getOrderState(terminal: Int?, branch: Int?): List<SummaryOrderState> {
        val orderStatesResponse = handleApi {
            OnyxRmsApi.orderRetrofitService.getOrderStateSummary(
                ApiRequest(
                    OrderRequestBody(
                        terminalNo = terminal.toString(),
                        branchNo = branch.toString()
                    )
                )
            )
        }
        if (orderStatesResponse is ApiSuccess) {
            return orderStatesResponse.body.data.asDomainOrderState()
        }
        return listOf()
    }

    suspend fun getSummaryItems(terminal: Int?, branch: Int?): List<SummaryItems> {
        val itemSummaryResponse = handleApi {
            OnyxRmsApi.orderRetrofitService.getOrderItemSummary(
                ApiRequest(
                    OrderRequestBody(
                        terminalNo = terminal.toString(),
                        branchNo = branch.toString()
                    )
                )
            )
        }
        if (itemSummaryResponse is ApiSuccess) {
            return itemSummaryResponse.body.data.asDomainSummaryItems()
        }
        return listOf()
    }
}