package com.example.android.chefapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.chefapp.domain.SummaryItems

@Entity
data class DatabaseSummaryItem (
    @PrimaryKey
    val code : Int,
    val name : String,
    val quantity: Int
)

fun List<DatabaseSummaryItem>.asDomainSummaryItems() : List<SummaryItems>{
    return this.map {
        SummaryItems(
            code= it.code,
            name= it.name,
            quantity = it.quantity
        )
    }
}