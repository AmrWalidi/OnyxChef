package com.example.android.chefapp.network.models.user

import com.example.android.chefapp.database.entity.DatabaseUser
import com.squareup.moshi.Json
import com.example.android.chefapp.domain.user.User
import com.example.android.chefapp.network.models.Setting

class NetworkUser(
    @Json(name = "USR_NM")
    val name: String,

    @Json(name = "U_ID")
    val id: String,

    @Json(name = "LOGIN")
    val login: String
)

fun NetworkUser.asDomainUser(): User {
    return User(
        userName = "$name - $id",
        loginDate = login.split(' ')[0].replace('/', '-'),
        unit = Setting.unit.toInt(),
        language = Setting.language
    )
}

fun NetworkUser.asDatabaseUser(): DatabaseUser {
    return DatabaseUser(
        id = id.toInt(),
        name = name,
        login = login.split(' ')[0].replace('/', '-'),
        unit = Setting.unit.toInt(),
        language = Setting.language
    )
}