package com.example.android.chefapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.chefapp.domain.SummaryOrderState
import com.example.android.chefapp.domain.SummaryOrderType

@Entity
data class DatabaseSummaryOrderType(
    @PrimaryKey
    val type: Int,
    val name: String,
    val count: Int
)

fun List<DatabaseSummaryOrderType>.asDomainOrderType(): List<SummaryOrderType> {
    return this.map {
        SummaryOrderType(
            name = it.name,
            count = it.count
        )
    }
}

@Entity
data class DatabaseSummaryOrderState(
    @PrimaryKey
    val state: String,
    val count: Int
)

fun List<DatabaseSummaryOrderState>.asDomainOrderState(): List<SummaryOrderState> {
    return this.map {
        SummaryOrderState(
            label = it.state,
            count = it.count
        )
    }
}