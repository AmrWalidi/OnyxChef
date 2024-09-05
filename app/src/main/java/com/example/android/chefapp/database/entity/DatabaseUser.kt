package com.example.android.chefapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.chefapp.domain.user.User
import com.example.android.chefapp.network.models.user.NetworkUser

@Entity
class DatabaseUser(
    @PrimaryKey
    val id: Int,
    val unit: Int,
    val language: String,
    val name: String,
    val login: String
)

fun DatabaseUser.asDomainUser(): User {
    return User(
        userName = "$name - $id",
        loginDate = login.split(' ')[0].replace('/', '-'),
        unit = unit,
        language = language
    )
}