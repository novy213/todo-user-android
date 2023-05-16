package com.example.todo

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.databinding.FragmentManageTaskBinding
import com.example.todo.databinding.FragmentRenameProjectBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ManageTask : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentManageTaskBinding;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myTextView.setText(currentTask.description)
        binding.ButtonDelete.setOnClickListener {
            RemoveTask()
        }
        binding.ButtonSave.setOnClickListener {
            Save()
        }
        binding.ButtonDone.setOnClickListener {
            Done()
        }
    }

    fun RemoveTask(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.removeTask("Bearer " + BEARER, currentTask.id)
        retrofitData.enqueue(object : Callback<APIResponse?> {
            override fun onResponse(
                call: Call<APIResponse?>,
                response: Response<APIResponse?>
            ) {
                dismiss()
            }

            override fun onFailure(call: Call<APIResponse?>, t: Throwable) {
                Log.d("Error   ", "onFailure: "+t.message)
            }
        })
    }

    fun Save(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val body = EditTaskBody(binding.myTextView.text.toString())
        val retrofitData = retrofitBuilder.editTask("Bearer " + BEARER, currentTask.id, body)
        retrofitData.enqueue(object : Callback<APIResponse?> {
            override fun onResponse(
                call: Call<APIResponse?>,
                response: Response<APIResponse?>
            ) {
                dismiss()
            }

            override fun onFailure(call: Call<APIResponse?>, t: Throwable) {
                Log.d("Error   ", "onFailure: "+t.message)
            }
        })
    }

    fun Done(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        var done = 0;
        if(currentTask.done==0) done=1;
        var body = MarkTaskBody(done)
        val retrofitData = retrofitBuilder.markTask("Bearer " + BEARER, currentTask.id, body)
        retrofitData.enqueue(object : Callback<APIResponse?> {
            override fun onResponse(
                call: Call<APIResponse?>,
                response: Response<APIResponse?>
            ) {
                dismiss()
            }

            override fun onFailure(call: Call<APIResponse?>, t: Throwable) {
                Log.d("Error   ", "onFailure: "+t.message)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentManageTaskBinding.inflate(inflater,container,false)
        return binding.root
    }
    private lateinit var bottomSheetListener: BottomSheetListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            bottomSheetListener = context as BottomSheetListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement BottomSheetListener")
        }
    }
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        bottomSheetListener.onBottomSheetClosed()
    }
}