package com.example.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    lateinit var binding:ActivityRegisterBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater);
    }

    fun Register_click(v:View){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val login = findViewById<TextView>(R.id.LoginText).text.toString()
        val pas = findViewById<TextView>(R.id.PasswordText).text.toString()
        val name = findViewById<TextView>(R.id.NameText).text.toString()
        val lname = findViewById<TextView>(R.id.LastNameText).text.toString()
        val body = RegisterBody(login, pas, name ,lname)
        val retrofitData = retrofitBuilder.register(body)
        retrofitData.enqueue(object : Callback<APIResponse?> {
            override fun onResponse(
                call: Call<APIResponse?>,
                response: Response<APIResponse?>
            ) {
                finish()
            }

            override fun onFailure(call: Call<APIResponse?>, t: Throwable) {
            }
        })
    }
    fun Back_click(v:View){
        finish()
    }
}