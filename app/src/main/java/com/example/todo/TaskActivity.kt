package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var tasks:List<Task>? = null
private lateinit var renameProjectModel: RenameProjectModel
var project_id = 0;

class TaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        val btnPop = findViewById<Button>(R.id.btnProject)
        val popupMenu = PopupMenu(this,btnPop)
        project_id = intent.getStringExtra("EXTRA_PROJECT_ID")!!.toInt()
        renameProjectModel = ViewModelProvider(this).get(RenameProjectModel::class.java)
        popupMenu.menuInflater.inflate(R.menu.project_menu_item, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem->
            val id = menuItem.itemId
            if(id==R.id.item_delete){
                deleteProject()
            }
            else if(id==R.id.item_rename){
                RenameProject_click()
            }
            false
        }
        btnPop.setOnClickListener {
            popupMenu.show()
        }
        getTasks()
    }

    fun RenameProject_click(){
        RenameProject().show(supportFragmentManager, "NewNameTag")
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.project_menu_item, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_delete -> {
                deleteProject()
                this.finish()
            }
        }
        return super.onContextItemSelected(item)
    }
    private fun deleteProject(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.deleteProject("Bearer " + BEARER, project_id)
        retrofitData.enqueue(object : Callback<APIResponse?> {
            override fun onResponse(
                call: Call<APIResponse?>,
                response: Response<APIResponse?>
            ) {
            }

            override fun onFailure(call: Call<APIResponse?>, t: Throwable) {
                Log.d("Error   ", "onFailure: "+t.message)
            }
        })
        this.finish()
    }
    fun getTasks() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.getTasks("Bearer " + BEARER, project_id)
        retrofitData.enqueue(object : Callback<Tasks?> {
            override fun onResponse(
                call: Call<Tasks?>,
                response: Response<Tasks?>
            ) {
                val responseBody = response.body()!!
                tasks = responseBody.tasks;
                if(!responseBody.error){
                    setTasks()
                }
            }

            override fun onFailure(call: Call<Tasks?>, t: Throwable) {
                Log.d("Error   ", "onFailure: "+t.message)
            }
        })
    }
    private fun setTasks() {
        val list = findViewById<ListView>(R.id.tasksListView);
        val tasksToList = mutableListOf<String>()
        var i=1;
        for (task in tasks!!) {
            tasksToList.add(i.toString() + ". " + task.description);
            i++
        }
        list.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tasksToList)
    }

    fun Back_click(v:View){
        this.finish()
    }
    fun AddTask_click(v:View){
        AddTask().show(supportFragmentManager, "AddTaskTag")
    }
    fun Refresh(v:View){
        getTasks()
    }
}