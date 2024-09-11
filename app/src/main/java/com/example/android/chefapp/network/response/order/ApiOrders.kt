package com.example.android.chefapp.network.response.order

import com.example.android.chefapp.database.entity.DatabaseOrder
import com.example.android.chefapp.database.entity.DatabaseOrderItem
import com.example.android.chefapp.database.entity.OrderItemCrossRef
import com.squareup.moshi.Json

data class ApiOrders(
    @Json(name = "OrderMasterList")
    val orders: List<NetworkOrder>?
)

data class NetworkOrder(
    @Json(name = "BILL_NO") val no: String,
    @Json(name = "ORDR_SRL") val serial: String,
    @Json(name = "BILL_DATE") val date: String,
    @Json(name = "BILL_TIME") val time: String,
    @Json(name = "ORDR_NOTE") val note: String,
    @Json(name = "BILL_DOC_TYPE") val docType: String,
    @Json(name = "BILL_DOC_TYPE_NM") val docTypeName: String,
    @Json(name = "PRCSSD_FLG") val processedFlag: String,
    @Json(name = "PRCSSD_U_ID") val processedUserId: String,
    @Json(name = "PRCSSD_DATE") val processedDate: String,
    @Json(name = "ORDR_STS") val status: String,
    @Json(name = "ordrDtl") val items: List<NetworkOrderItem>
)

data class NetworkOrderItem(
    @Json(name = "ITM_SRL") val serial: String,
    @Json(name = "I_CODE") val code: String,
    @Json(name = "ITM_NM") val name: String,
    @Json(name = "ITM_UNT") val quantity: String,
    @Json(name = "ITM_STS") val status: String
)

fun ApiOrders.asDatabaseOrders(): Array<DatabaseOrder> {
    return orders?.map {
        DatabaseOrder(
            orderId = it.no.toInt(),
            serial = it.serial,
            date = it.date,
            time = it.time,
            note = it.note,
            type = it.docTypeName,
            status = when (it.status) {
                "1" -> "New"
                "2" -> "Change"
                else -> "delay"
            }
        )
    }?.toTypedArray() ?: arrayOf()
}

fun ApiOrders.asDatabaseItems(): Array<DatabaseOrderItem> {
    val items: MutableList<DatabaseOrderItem> = mutableListOf()
    orders?.forEach { order ->
        order.items.forEach { item ->
            val itemName: String = item.name
            var x = 1
            items.forEach {
                if (it.name == itemName)
                    x = 2
            }
            if (x == 1) {
                items.add(
                    DatabaseOrderItem(
                        itemId = item.serial,
                        code = item.code,
                        name = item.name,
                    )
                )
            }
        }
    }
    return items.toTypedArray()
}

fun ApiOrders.asDatabaseOrderItem(): Array<OrderItemCrossRef> {
    val orderItemList: MutableList<OrderItemCrossRef> = mutableListOf()
    orders?.map { order ->
        val orderId = order.no.toInt()
        order.items.map { item ->
            orderItemList.add(
                OrderItemCrossRef(
                    orderId = orderId,
                    itemId = item.name,
                    quantity = item.quantity,
                    status = when (item.status) {
                        "1" -> "New"
                        "2" -> "Change"
                        else -> "delay"
                    }
                )
            )
        }
    }
    return orderItemList.toTypedArray()
}
