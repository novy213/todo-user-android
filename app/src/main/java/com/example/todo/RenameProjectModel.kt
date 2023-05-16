package com.example.todo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RenameProjectModel:ViewModel() {
    var project_name = MutableLiveData<String>()
}