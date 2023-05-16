package com.example.todo

data class Projects(
    val apiResponse: APIResponse,
    val projects: List<Project>?
)  : APIResponse(apiResponse.error,apiResponse.message)
