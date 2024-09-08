package com.example.android.chefapp.database

import android.content.Context
import androidx.room.*
import com.example.android.chefapp.database.dao.OrderDao
import com.example.android.chefapp.database.dao.UserDao
import com.example.android.chefapp.database.entity.DatabaseOrder
import com.example.android.chefapp.database.entity.DatabaseOrderItem
import com.example.android.chefapp.database.entity.DatabaseSummaryItem
import com.example.android.chefapp.database.entity.DatabaseSummaryOrderState
import com.example.android.chefapp.database.entity.DatabaseSummaryOrderType
import com.example.android.chefapp.database.entity.DatabaseUser
import com.example.android.chefapp.database.entity.OrderItemCrossRef

@Database(
    entities = [
        DatabaseUser::class,
        DatabaseOrder::class,
        DatabaseOrderItem::class,
        OrderItemCrossRef::class,
        DatabaseSummaryOrderType::class,
        DatabaseSummaryOrderState::class,
        DatabaseSummaryItem::class
    ],
    version = 5
)
abstract class OnyxChefDatabase : RoomDatabase() {
    abstract val daoUser: UserDao
    abstract val daoOrder: OrderDao
}

private lateinit var INSTANCE: OnyxChefDatabase

fun getDatabase(context: Context): OnyxChefDatabase {
    synchronized(OnyxChefDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                OnyxChefDatabase::class.java,
                "onyx"
            ).fallbackToDestructiveMigration().build()
        }
    }
    return INSTANCE
}