package com.example.todo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.databinding.FragmentCreateProjectBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CreateProject : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCreateProjectBinding
    private lateinit var createProjectModel: CreateProjectModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        createProjectModel = ViewModelProvider(activity).get(CreateProjectModel::class.java)
        binding.CreateProjectButton.setOnClickListener {
            createProject()
        }
    }

    private fun createProject() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val createProjectBody = CreateProjectBody(binding.ProjectNameText.text.toString(), user_id)
        val retrofitData = retrofitBuilder.createProject("Bearer " + BEARER , createProjectBody)
        retrofitData.enqueue(object : Callback<APIResponse?> {
            override fun onResponse(
                call: Call<APIResponse?>,
                response: Response<APIResponse?>
            ) {
                val responseBody = response.body()!!
                if(!responseBody.error){
                    binding.ProjectNameText.setText("")
                    dismiss()
                }
            }

            override fun onFailure(call: Call<APIResponse?>, t: Throwable) {
                binding.ProjectNameText.setText(t.message)
                Log.d("Error", "onFailure: "+t.message)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateProjectBinding.inflate(inflater,container,false)
        return binding.root
    }
}