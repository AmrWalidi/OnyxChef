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

fun List<DatabaseUser?>.asDomainUser(): User? {
    val user = this.firstOrNull() ?: return null

    return user.let {
        User(
            username = "${it.name} - ${it.id}",
            loginDate = it.login.split(' ')[0].replace('/', '-'),
            unit = it.unit,
            language = it.language,
            terminal = it.terminal,
            branch = it.branch
        )
    }
}