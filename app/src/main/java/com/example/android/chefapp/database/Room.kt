package com.example.android.chefapp.database

import android.content.Context
import androidx.room.*
import com.example.android.chefapp.database.dao.UserDao
import com.example.android.chefapp.database.entity.DatabaseUser

@Database(entities = [DatabaseUser::class], version = 1)
abstract class OnyxChefDatabase : RoomDatabase(){
    abstract val userDao : UserDao
}

private lateinit var INSTANCE: OnyxChefDatabase

fun getDatabase(context: Context): OnyxChefDatabase {
    synchronized(OnyxChefDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                OnyxChefDatabase::class.java,
                "onyx").build()
        }
    }
    return INSTANCE
}