package com.example.android.chefapp.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class DatabaseOrder(
    @PrimaryKey
    val orderId: Int,
    val serial: String,
    val date: String,
    val time: String,
    val note: String,
    val type: String,
    val status: String,
)

data class OrderWithItems(
    @Embedded val order: DatabaseOrder,

    @Relation(
        parentColumn = "orderId",
        entityColumn = "itemId",
        associateBy = Junction(OrderItemCrossRef::class)
    )
    val items: List<DatabaseOrderItem>
)

@Entity(primaryKeys = ["orderId", "itemId"])
data class OrderItemCrossRef(
    val orderId: Int,
    val itemId: String,
    val quantity : Int,
    val status: String
)


@Entity
data class DatabaseOrderItem(
    @PrimaryKey
    val itemId: String,
    val code: String,
    val name: String,
)

data class ItemWithOrders(
    @Embedded val item: DatabaseOrderItem,

    @Relation(
        parentColumn = "itemId",
        entityColumn = "orderId",
        associateBy = Junction(OrderItemCrossRef::class)
    )
    val orders: List<DatabaseOrder>
)