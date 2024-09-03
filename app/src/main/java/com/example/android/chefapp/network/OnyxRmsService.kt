package com.example.android.chefapp.network

import com.example.android.chefapp.network.service.OrderOnyxRmsService
import com.example.android.chefapp.network.service.UserOnyxRmsService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://mdev.yemensoft.net:473/onyxrmsservice/api/OnyxChef/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


object OnyxRmsApi {
    val orderRetrofitService: OrderOnyxRmsService by lazy {
        retrofit.create(OrderOnyxRmsService::class.java)
    }

    val userRetrofitService: UserOnyxRmsService by lazy {
        retrofit.create(UserOnyxRmsService::class.java)
    }
}