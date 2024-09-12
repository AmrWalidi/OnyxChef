package com.example.android.chefapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseOrder(
    @PrimaryKey
    val orderId: Int,
    val serial: String,
    val date: String,
    val time: String,
    val note: String,
    val passedTime : String,
    val type: String,
    val status: String,
)


@Entity(primaryKeys = ["orderId", "itemId"])
data class OrderItemCrossRef(
    val orderId: Int,
    val itemId: String,
    val quantity : String,
    val status: String
)

@Entity
data class DatabaseOrderItem(
    @PrimaryKey
    val itemId: String,
    val code: String,
    val name: String,
)
