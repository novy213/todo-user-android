package com.example.todo

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterface {
    @GET("todo")
    fun getProjects(@Header("Authorization") authToken: String): Call<Projects>
}