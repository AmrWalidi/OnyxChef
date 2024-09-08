package com.example.android.chefapp.repository

import com.example.android.chefapp.database.OnyxChefDatabase
import com.example.android.chefapp.database.entity.asDomainUser
import com.example.android.chefapp.domain.User
import com.example.android.chefapp.network.OnyxRmsApi
import com.example.android.chefapp.network.request.ApiRequest
import com.example.android.chefapp.network.request.OrderRequestBody
import com.example.android.chefapp.network.response.user.asDatabaseUser
import com.example.android.chefapp.network.response.user.asDomainUser
import com.example.android.chefapp.network.request.UserRequestBody
import com.example.android.chefapp.network.response.ApiResponse
import com.example.android.chefapp.network.response.order.ApiOrders
import com.example.android.chefapp.network.response.order.asDatabaseItems
import com.example.android.chefapp.network.response.order.asDatabaseOrderItem
import com.example.android.chefapp.network.response.order.asDatabaseOrders
import com.example.android.chefapp.network.response.user.ApiUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val database: OnyxChefDatabase) {

    suspend fun getCurrentUser(): User? {
        return database.daoUser.get().asDomainUser()
    }

    suspend fun logout(user: User) {
        return database.daoUser.delete(user.unit)
    }

    suspend fun login(password: String): User? {
        val response: ApiResponse<ApiUser>
        withContext(Dispatchers.IO) {
            response = OnyxRmsApi.userRetrofitService
                .getUserDetails(ApiRequest(UserRequestBody(password = password)))

        }
        val user = when (response.result.errNo) {
            0 -> {
                database.daoUser.insert(response.data.user.asDatabaseUser())
                response.data.user.asDomainUser()
            }

            else -> null
        }
        return user
    }

    suspend fun refreshOrders(user: User) {
        val response: ApiResponse<ApiOrders>
        withContext(Dispatchers.IO) {
            response = OnyxRmsApi.orderRetrofitService
                .getOrders(
                    ApiRequest(
                        OrderRequestBody(
                            branchNo = user.branch.toString(),
                            terminalNo = user.terminal.toString()
                        )
                    )
                )
            val databaseOrders = response.data.asDatabaseOrders()
            val databaseItem = response.data.asDatabaseItems()
            val databaseOrderItem = response.data.asDatabaseOrderItem()
            database.daoOrder.insertAllOrder(*databaseOrders)
            database.daoOrder.insertAllItem(*databaseItem)
            database.daoOrder.insertAllOrderItemCrossRef(*databaseOrderItem)
        }
    }

    suspend fun getOrders(): String {
        var n: Int
        var s = ""
        withContext(Dispatchers.IO) {
            val ordersWithItems = database.daoOrder.getOrderWithItems()
            val orderItemCross = database.daoOrder.getOrderItemCrossRef()
            orderItemCross.forEach {
                s += "${it.status} - ${it.quantity}"
            }
        }
        return s
    }

}