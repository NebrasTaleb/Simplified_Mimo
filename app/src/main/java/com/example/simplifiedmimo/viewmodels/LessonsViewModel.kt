package com.example.simplifiedmimo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplifiedmimo.data.LessonsRepository
import kotlinx.coroutines.launch

class LessonsViewModel : ViewModel() {

    private lateinit var lessonsRepository: LessonsRepository

    init {
        // launch a Coroutine that will be canceled when the ViewModel is cleared
        viewModelScope.launch {

        }
    }
}