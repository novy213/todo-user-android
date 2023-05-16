package com.example.todo

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.databinding.FragmentAddTaskBinding
import com.example.todo.databinding.FragmentRenameProjectBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddTask : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentAddTaskBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        binding.AddTaskButton.setOnClickListener {
            AddTaskFunction()
        }

    }

    private fun AddTaskFunction() {
        val al = AlertDialog.Builder(this.context)
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val body = AddTaskBody(binding.TaskDescription.text.toString())
        val retrofitData = retrofitBuilder.addTask("Bearer " + BEARER, project_id, body)
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
        binding = FragmentAddTaskBinding.inflate(inflater,container,false)
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
interface BottomSheetListener {
    fun onBottomSheetClosed()
}