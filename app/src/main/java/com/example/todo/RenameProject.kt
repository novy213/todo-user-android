package com.example.todo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.todo.databinding.FragmentRenameProjectBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RenameProject : BottomSheetDialogFragment() {
    private lateinit var binding : FragmentRenameProjectBinding
    private lateinit var renameProjectViewModel : RenameProjectModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        renameProjectViewModel = ViewModelProvider(activity).get(RenameProjectModel::class.java)
        binding.RenameProjectButton.setOnClickListener {
            Rename()
        }
    }

    private fun Rename(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val body = RenameProjectBody(binding.ProjectNewNameText.text.toString())
        val retrofitData = retrofitBuilder.renameProject("Bearer " + BEARER, project_id,body)
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
        dismiss()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRenameProjectBinding.inflate(inflater,container,false)
        return binding.root
    }
}