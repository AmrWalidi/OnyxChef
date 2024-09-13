package com.example.android.chefapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.chefapp.database.entity.DatabaseOrder
import com.example.android.chefapp.database.entity.DatabaseOrderItem
import com.example.android.chefapp.database.entity.OrderItemCrossRef

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrders(vararg entities: DatabaseOrder)

    @Query("SELECT * FROM databaseorder LIMIT 4 OFFSET :offset ;")
    suspend fun getOrders(offset: Int): List<DatabaseOrder>

    @Query("SELECT COUNT(*) FROM databaseorder")
    suspend fun getOrdersNumber(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(vararg entities: DatabaseOrderItem)

    @Query("SELECT * FROM databaseorderitem WHERE itemId = :id ;")
    suspend fun getItem(id: String): DatabaseOrderItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllOrderItemCrossRef(vararg crossRef: OrderItemCrossRef)

    @Query("SELECT * FROM OrderItemCrossRef WHERE itemId = :itemId")
    suspend fun getOrderItemCrossRef(itemId: Int): List<OrderItemCrossRef>

    @Query("DELETE FROM databaseuser WHERE unit = :id")
    suspend fun delete(id: Int)
}