package com.example.android.chefapp.domain

data class Order(
    val no: Int,
    val serial: String,
    val date: String,
    val time: String,
    val note: String,
    val type: String,
    val status: String,
    val items: List<OrderItem>
)

data class OrderItem(
    val name: String,
    val quantity: String,
    val status: String
)