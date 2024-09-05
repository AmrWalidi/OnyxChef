package com.example.android.chefapp.database.dao

import androidx.room.*
import com.example.android.chefapp.database.entity.DatabaseUser

@Dao
interface UserDao {
    @Query("select * from databaseuser")
    suspend fun getUser(): DatabaseUser?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user : DatabaseUser)
}