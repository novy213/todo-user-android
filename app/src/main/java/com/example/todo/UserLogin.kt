package com.example.todo

data class UserLogin(
    val error: Boolean,
    val message: Any,
    val token: String,
    val userId: Int
)