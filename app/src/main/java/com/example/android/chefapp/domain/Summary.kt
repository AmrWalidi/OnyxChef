package com.example.android.chefapp.domain

data class SummaryOrderType(val name: String, val count: Int)

data class SummaryOrderState(
    val label: String,
    val count: Int,
)

data class SummaryItems(val code: Int, val name: String, val quantity: Int)