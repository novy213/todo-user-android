package com.example.todo

data class Projects(
    val error: Boolean,
    val message: String,
    val projects: List<Project>
)