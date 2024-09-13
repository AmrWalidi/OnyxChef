package com.example.android.chefapp.repository

import com.example.android.chefapp.database.OnyxChefDatabase
import com.example.android.chefapp.database.entity.DatabaseOrder
import com.example.android.chefapp.domain.Order
import com.example.android.chefapp.domain.OrderItem
import com.example.android.chefapp.network.ApiSuccess
import com.example.android.chefapp.network.OnyxRmsApi
import com.example.android.chefapp.network.handleApi
import com.example.android.chefapp.network.request.ApiRequest
import com.example.android.chefapp.network.request.OrderRequestBody
import com.example.android.chefapp.network.response.order.asDatabaseItems
import com.example.android.chefapp.network.response.order.asDatabaseOrderItem
import com.example.android.chefapp.network.response.order.asDatabaseOrders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrdersRepository(val database: OnyxChefDatabase) {

    private suspend fun ordersAsDomain(
        databaseOrders: List<DatabaseOrder>
    ): List<Order> {
        return databaseOrders.map { order ->
            val orderItemsCrossRef = withContext(Dispatchers.IO) {
                database.daoOrder.getOrderItemCrossRef(order.orderId)
            }
            Order(
                no = order.orderId,
                serial = order.serial,
                date = order.date,
                time = order.time,
                note = order.note,
                type = order.type,
                status = order.status,
                passedTime = order.passedTime,
                items = orderItemsCrossRef.filter {
                    it.orderId == order.orderId
                }.map { ref ->
                    OrderItem(
                        name = ref.itemId,
                        quantity = ref.quantity,
                        status = ref.status
                    )
                }
            )
        }
    }


    suspend fun refreshOrders(branch: Int?, terminal: Int?): Int {
        val response = handleApi {
            OnyxRmsApi.orderRetrofitService
                .getOrders(
                    ApiRequest(
                        OrderRequestBody(
                            branchNo = branch.toString(),
                            terminalNo = terminal.toString()
                        )
                    )
                )
        }
        return when (response) {
            is ApiSuccess -> {
                val databaseOrders = response.body.data.asDatabaseOrders()
                val databaseItem = response.body.data.asDatabaseItems()
                val databaseOrderItem = response.body.data.asDatabaseOrderItem()
                database.daoOrder.insertOrders(*databaseOrders)
                database.daoOrder.insertItems(*databaseItem)
                database.daoOrder.insertAllOrderItemCrossRef(*databaseOrderItem)
                response.body.data.orders?.size ?: 0
            }

            else -> {
                0
            }
        }
    }

    suspend fun getOrders(currentPage: Int): List<Order> {
        return ordersAsDomain(database.daoOrder.getOrders(currentPage * 4))

    }

    suspend fun getOrdersNumber(): Int {
        return database.daoOrder.getOrdersNumber()

    }
}