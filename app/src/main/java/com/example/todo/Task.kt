package com.example.todo

data class Task(
    val description: String,
    var done: Int,
    val id: Int,
    val project_id: Int
)