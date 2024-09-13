package com.example.android.chefapp.network.service

import com.example.android.chefapp.network.request.ApiRequest
import com.example.android.chefapp.network.request.OrderRequestBody
import com.example.android.chefapp.network.request.ProcessedRequestBody
import com.example.android.chefapp.network.response.ApiResponse
import com.example.android.chefapp.network.response.order.ApiOrderItemSummary
import com.example.android.chefapp.network.response.order.ApiOrderStateSummary
import com.example.android.chefapp.network.response.order.ApiOrderTypeSummary
import com.example.android.chefapp.network.response.order.ApiOrders
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface OrderOnyxRmsService {

    @POST("Order/GetOrders")
    suspend fun getOrders(@Body post: ApiRequest<OrderRequestBody>): Response<ApiResponse<ApiOrders>>

    @POST("Order/GetOrderTypeSummry")
    suspend fun getOrderTypeSummary(@Body post: ApiRequest<OrderRequestBody>): Response<ApiResponse<ApiOrderTypeSummary>>

    @POST("Order/GetOrderStateSummry")
    suspend fun getOrderStateSummary(@Body post: ApiRequest<OrderRequestBody>): Response<ApiResponse<ApiOrderStateSummary>>

    @POST("Order/GetOrderItemSummay")
    suspend fun getOrderItemSummary(@Body post: ApiRequest<OrderRequestBody>): Response<ApiResponse<ApiOrderItemSummary>>

    @POST("Order/SetItemProcessed")
    suspend fun setItemProcessed(@Body post: ApiRequest<ProcessedRequestBody>): Response<ApiResponse<Any>>

    @POST("Order/SetOrderProcessed")
    suspend fun setOrderProcessed(@Body post: ApiRequest<ProcessedRequestBody>): Response<ApiResponse<Any>>

    @POST("Order/RecallProcessedOrder")
    suspend fun recallProcessedOrder(@Body post: ApiRequest<ProcessedRequestBody>): Response<ApiResponse<Any>>
}