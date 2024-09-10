package com.example.android.chefapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.chefapp.domain.User

@Entity
class DatabaseUser(
    @PrimaryKey
    val id: Int,
    val unit: Int,
    val language: String,
    val name: String,
    val login: String,
    val terminal: Int,
    val branch: Int
)

fun DatabaseUser?.asDomainUser(): User? {

    return this?.login?.split(' ')?.get(0)?.replace('/', '-')?.let {
        User(
        username = "$name - $id",
        loginDate = it,
        unit = unit,
        language = language,
        terminal = terminal,
        branch = branch
    )
    }
}
