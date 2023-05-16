package com.example.todo

data class Task(
    val description: String,
    val done: Int,
    val id: Int,
    val project_id: Int
)