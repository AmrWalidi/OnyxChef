package com.example.android.chefapp.database.dao

import androidx.room.*
import com.example.android.chefapp.database.entity.DatabaseUser

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: DatabaseUser)

    @Query("select * from databaseuser")
    suspend fun get(): DatabaseUser?

    @Query("DELETE FROM databaseuser WHERE unit = :id" )
    suspend fun delete(id: Int)

}