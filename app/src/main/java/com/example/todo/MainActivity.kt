 package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

 const val BASE_URL = "http:192.168.56.1/basic/web/api/"

 lateinit var projects:List<Project>

 class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getProjects()
    }

     private fun getProjects() {
         val retrofitBuilder = Retrofit.Builder()
             .addConverterFactory(GsonConverterFactory.create())
             .baseUrl(BASE_URL)
             .build()
             .create(ApiInterface::class.java)
         val retrofitData = retrofitBuilder.getProjects("Bearer a8avT5kODS403pBxDg-qoj8PpMbYh1Ab")
         retrofitData.enqueue(object : Callback<Projects?> {
             override fun onResponse(
                 call: Call<Projects?>,
                 response: Response<Projects?>
             ) {
                 val responseBody = response.body()!!
                 projects = responseBody.projects;
                 val projectsToList = listOf<String>()
                 for(project in projects){
                     projectsToList.plus(project.id.toString() +". "+ project.project_name)
                 }
                 val list = findViewById<ListView>(R.id.projectsListView);

                 //list.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, projectsToList)

             }

             override fun onFailure(call: Call<Projects?>, t: Throwable) {
                 Log.d("Error   ", "onFailure: "+t.message)
             }
         })
     }
 }