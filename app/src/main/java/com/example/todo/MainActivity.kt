package com.example.todo

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.todo.databinding.ActivityMainBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http:65.21.6.59/basic/web/api/"
var BEARER = ""
var user_id:Int = 0;
var projects:List<Project>? = null

private lateinit var binding: ActivityMainBinding
private lateinit var createProjectModel: CreateProjectModel

class MainActivity : AppCompatActivity(), BottomSheetListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        createProjectModel = ViewModelProvider(this).get(CreateProjectModel::class.java)

        val preferences = getSharedPreferences("Token", Context.MODE_PRIVATE)
        BEARER = preferences.getString("token", "").toString()
        user_id = preferences.getInt("user_id", 0)

        if(BEARER!="") {
            try {
                getProjects()
            }catch (ex: Exception){}
        }
        else {
            val intent = Intent(this, Login::class.java)
            startActivityForResult(intent,1)
        }
    }
    fun CreateProject_click(v:View){
     CreateProject().show(supportFragmentManager, "CreateProjectTag");
    }
    private fun getProjects() {
     val retrofitBuilder = Retrofit.Builder()
         .addConverterFactory(GsonConverterFactory.create())
         .baseUrl(BASE_URL)
         .build()
         .create(ApiInterface::class.java)
     val retrofitData = retrofitBuilder.getProjects("Bearer " + BEARER)
     retrofitData.enqueue(object : Callback<Projects?> {
         override fun onResponse(
             call: Call<Projects?>,
             response: Response<Projects?>
         ) {
             try {
                 val responseBody = response.body()!!
                 projects = responseBody.projects;
                 SetProjects()
             }catch(ex:Exception){}
         }
         override fun onFailure(call: Call<Projects?>, t: Throwable) {
             Log.d("Error", "onFailure: "+t.message)
         }
     })
    }
    private fun SetProjects(){
     val list = findViewById<ListView>(R.id.projectsListView);
     val projectsToList = mutableListOf<String>()
     var i=1;
     for(project in projects!!){
         projectsToList.add(i.toString() +". "+ project.project_name);
         i++
     }
     list.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,projectsToList)
      list.setOnItemClickListener { parent, view, position, id ->
         val project_id = projects!![position].id.toString()
         val intent = Intent(this, TaskActivity::class.java).also {
             it.putExtra("EXTRA_PROJECT_ID", project_id)
             startActivityForResult(it, 0)
         }
     }
    }
    fun Logout_click(v:View){
     val retrofitBuilder = Retrofit.Builder()
         .addConverterFactory(GsonConverterFactory.create())
         .baseUrl(BASE_URL)
         .build()
         .create(ApiInterface::class.java)
     val retrofitData = retrofitBuilder.logout("Bearer " + BEARER)
     retrofitData.enqueue(object : Callback<APIResponse?> {
         override fun onResponse(
             call: Call<APIResponse?>,
             response: Response<APIResponse?>
         ) {
            BEARER = "";
            user_id=0
            val preferences = getSharedPreferences("Token", MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString("token", "")
            editor.putInt("user_id", 0)
            editor.apply()
             binding.projectsListView.adapter = null
         }

         override fun onFailure(call: Call<APIResponse?>, t: Throwable) {
             BEARER = "";
             user_id=0
             val preferences = getSharedPreferences("Token", MODE_PRIVATE)
             val editor = preferences.edit()
             editor.putString("token", "")
             editor.apply()
         }
     })
     launchLogin()
    }
    fun launchLogin(){
        val intent2 = Intent(this, Login::class.java)
        startActivityForResult(intent2,1)
    }
    public fun Refresh_click(v:View){
        getProjects()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == RESULT_OK) {
            getProjects()
        }
        if (requestCode == 1 && resultCode == RESULT_OK) {
            getProjects()
        }
    }
    override fun onBottomSheetClosed() {
        getProjects()
    }
}
