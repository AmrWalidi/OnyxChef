package com.example.android.chefapp.domain

data class User (
    val username : String,
    val loginDate : String,
    val unit : Int,
    val language : String,
    val terminal : Int,
    val branch : Int
)