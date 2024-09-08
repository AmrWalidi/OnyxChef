package com.example.android.chefapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.android.chefapp.database.entity.DatabaseOrder
import com.example.android.chefapp.database.entity.DatabaseOrderItem
import com.example.android.chefapp.database.entity.DatabaseSummaryItem
import com.example.android.chefapp.database.entity.DatabaseSummaryOrderState
import com.example.android.chefapp.database.entity.DatabaseSummaryOrderType
import com.example.android.chefapp.database.entity.OrderItemCrossRef
import com.example.android.chefapp.database.entity.OrderWithItems

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllOrder(vararg entities: DatabaseOrder)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllItem(vararg entities: DatabaseOrderItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllOrderItemCrossRef(vararg crossRef: OrderItemCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSummaryOrderType(vararg entities: DatabaseSummaryOrderType)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSummaryOrderState(vararg entities: DatabaseSummaryOrderState)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSummaryOrderItems(vararg entities: DatabaseSummaryItem)

    @Transaction
    @Query("SELECT * FROM databaseorder")
    suspend fun getOrderWithItems(): List<OrderWithItems>

    @Query("SELECT * FROM OrderItemCrossRef")
    suspend fun getOrderItemCrossRef(): List<OrderItemCrossRef>

    @Query("SELECT * FROM databasesummaryordertype")
    suspend fun getSummaryOrderType(): List<DatabaseSummaryOrderType>

    @Query("SELECT * FROM databasesummaryorderstate")
    suspend fun getSummaryOrderState(): List<DatabaseSummaryOrderState>

    @Query("SELECT * FROM databasesummaryitem")
    suspend fun getSummaryItems(): List<DatabaseSummaryItem>

    @Query("DELETE FROM databaseuser WHERE unit = :id")
    suspend fun delete(id: Int)
}