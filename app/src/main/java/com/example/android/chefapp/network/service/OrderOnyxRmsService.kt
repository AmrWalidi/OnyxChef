package com.example.android.chefapp.network.service

import com.example.android.chefapp.network.request.ApiRequest
import com.example.android.chefapp.network.request.OrderRequestBody
import com.example.android.chefapp.network.request.ProcessedRequestBody
import com.example.android.chefapp.network.response.ApiResponse
import com.example.android.chefapp.network.response.order.ApiOrderItemSummary
import com.example.android.chefapp.network.response.order.ApiOrderStateSummary
import com.example.android.chefapp.network.response.order.ApiOrderTypeSummary
import com.example.android.chefapp.network.response.order.ApiOrders
import retrofit2.http.Body
import retrofit2.http.POST


interface OrderOnyxRmsService {

    @POST("Order/GetOrders")
    suspend fun getOrders(@Body post: ApiRequest<OrderRequestBody>): ApiResponse<ApiOrders>

    @POST("Order/GetOrderTypeSummary")
    suspend fun getOrderTypeSummary(@Body post: ApiRequest<OrderRequestBody>): ApiResponse<ApiOrderTypeSummary>

    @POST("Order/GetOrderTypeSummary")
    suspend fun getOrderStateSummary(@Body post: ApiRequest<OrderRequestBody>): ApiResponse<ApiOrderStateSummary>

    @POST("Order/GetOrderItemSummary")
    suspend fun getOrderItemSummary(@Body post: ApiRequest<OrderRequestBody>): ApiResponse<ApiOrderItemSummary>

    @POST("Order/SetItemProcessed")
    suspend fun setItemProcessed(@Body post: ApiRequest<ProcessedRequestBody>): ApiResponse<Any>

    @POST("Order/SetOrderProcessed")
    suspend fun setOrderProcessed(@Body post: ApiRequest<ProcessedRequestBody>): ApiResponse<Any>

    @POST("Order/RecallProcessedOrder")
    suspend fun recallProcessedOrder(@Body post: ApiRequest<ProcessedRequestBody>): ApiResponse<Any>
}