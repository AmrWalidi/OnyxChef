package com.example.android.chefapp.domain

import android.os.Parcel
import android.os.Parcelable

data class User(
    val username: String,
    val loginDate: String,
    val unit: Int,
    val language: String,
    val terminal: Int,
    val branch: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(username)
        dest.writeString(loginDate)
        dest.writeInt(unit)
        dest.writeString(language)
        dest.writeInt(terminal)
        dest.writeInt(branch)
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}