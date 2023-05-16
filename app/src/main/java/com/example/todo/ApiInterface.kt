package com.example.todo

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiInterface {
    @GET("todo")
    fun getProjects(@Header("Authorization") authToken: String): Call<Projects>
    @GET("todo/{project_id}")
    fun getTasks(@Header("Authorization") authToken: String , @Path("project_id") project_id: Int): Call<Tasks>
    @POST("todo")
    fun login(@Body user: UserLoginBody): Call<UserLogin>
    @DELETE("todo")
    fun logout(@Header("Authorization") authToken: String) :Call<APIResponse>
    @POST("todo/createproject")
    fun createProject(@Header("Authorization") authToken: String, @Body project: CreateProjectBody) : Call<APIResponse>
    @DELETE("todo/{project_id}")
    fun deleteProject(@Header("Authorization") authToken: String , @Path("project_id") project_id: Int) : Call<APIResponse>
    @PUT("todo/{project_id}")
    fun renameProject(@Header("Authorization") authToken: String , @Path("project_id") project_id: Int, @Body rename:RenameProjectBody) : Call<APIResponse>
    @POST("todo/{project_id}")
    fun addTask(@Header("Authorization") authToken: String , @Path("project_id") project_id: Int, @Body add:AddTaskBody) : Call<APIResponse>
    @DELETE("todo/project/{task_id}")
    fun removeTask(@Header("Authorization") authToken: String,@Path("task_id") task_id: Int) : Call<APIResponse>
    @PUT("todo/project/{task_id}")
    fun editTask(@Header("Authorization") authToken: String,@Path("task_id") task_id: Int, @Body body:EditTaskBody) : Call<APIResponse>
    @POST("todo/project/{task_id}")
    fun markTask(@Header("Authorization") authToken: String,@Path("task_id") task_id: Int, @Body body:MarkTaskBody) : Call<APIResponse>
    @POST("todo/register")
    fun register(@Body body:RegisterBody) : Call<APIResponse>
}