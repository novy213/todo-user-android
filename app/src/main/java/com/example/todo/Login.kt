package com.example.todo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun Login_click(v:View){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val login = findViewById<TextView>(R.id.loginText).text.toString()
        val password = findViewById<TextView>(R.id.passwordText).text.toString()
        val user = UserLoginBody(login, password)
        val retrofitData = retrofitBuilder.login(user)
        retrofitData.enqueue(object : Callback<UserLogin?> {
            override fun onResponse(
                call: Call<UserLogin?>,
                response: Response<UserLogin?>
            ) {
                val responseBody = response.body()!!
                if(!responseBody.error){
                    val preferences = getSharedPreferences("Token", MODE_PRIVATE)
                    val editor = preferences.edit()
                    editor.putString("token", responseBody.token)
                    editor.putInt("user_id", responseBody.userId)
                    editor.apply()
                    BEARER = responseBody.token;
                    user_id = responseBody.userId;
                }
                back()
            }

            override fun onFailure(call: Call<UserLogin?>, t: Throwable) {
                Log.d("Error", "onFailure: "+t.message)
            }
        })
    }
    private fun back(){
        val resultIntent = Intent()
        val resultCode = RESULT_OK
        resultIntent.putExtra("key", "wartość")
        setResult(resultCode, resultIntent)
        finish()
    }
    fun Register(v:View){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
    fun Quit(v:View){
        finishAffinity()
    }
}