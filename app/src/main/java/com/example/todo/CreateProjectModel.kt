package com.example.todo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateProjectModel:ViewModel() {
    var project_name = MutableLiveData<String>()
    var user_id = MutableLiveData<String>()
}