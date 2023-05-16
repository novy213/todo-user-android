package com.example.todo

data class Tasks(
    val apiResponse: APIResponse,
    val tasks: List<Task>?
) : APIResponse(apiResponse.error,apiResponse.message)