package com.example.android.chefapp.network.response.user

import com.example.android.chefapp.database.entity.DatabaseUser
import com.example.android.chefapp.domain.User
import com.example.android.chefapp.network.request.Setting
import com.squareup.moshi.Json

data class ApiUser(
    @Json(name = "UserData")
    val user: NetworkUser
)

data class NetworkUser(
    @Json(name = "USR_NM")
    val name: String,

    @Json(name = "U_ID")
    val id: String,

    @Json(name = "LOGIN")
    val login: String,

    @Json(name = "TRMNL_NO")
    val terminalNo: String,

    @Json(name = "CONN_BRN_NO")
    val branchNo: String
)

fun NetworkUser.asDomainUser(): User {
    return User(
        username = "$name - $id",
        loginDate = login.split(' ')[0].replace('/', '-'),
        unit = Setting.unit,
        language = when (Setting.language) {
            1 -> "Arabic"
            2 -> "English"
            3 -> "French"
            else -> "not a valid language"
        },
        terminal = terminalNo.toInt(),
        branch = branchNo.toInt()
    )
}

fun NetworkUser.asDatabaseUser(): DatabaseUser {
    return DatabaseUser(
        id = id.toInt(),
        name = name,
        login = login.split(' ')[0].replace('/', '-'),
        unit = Setting.unit,
        language = when (Setting.language) {
            1 -> "Arabic"
            2 -> "English"
            3 -> "French"
            else -> "not a valid language"
        },
        terminal = terminalNo.toInt(),
        branch = branchNo.toInt()
    )
}
