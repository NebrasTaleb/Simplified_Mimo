package com.example.simplifiedmimo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplifiedmimo.data.LessonsRepository
import com.example.simplifiedmimo.data.api.models.Lesson
import kotlinx.coroutines.launch

class LessonsViewModel : ViewModel() {

    private var lessonsRepository: LessonsRepository = LessonsRepository()
    private val _lessons = MutableLiveData<List<Lesson>>()
    val lessons: LiveData<List<Lesson>> = _lessons

    init {
        // launch a Coroutine that will be canceled when the ViewModel is cleared
        viewModelScope.launch {
            // ask the repository to get the lessons and assign the returned result to the mutable live data
            _lessons.value = lessonsRepository.getLessons()
//            _lessons.value = lessonsRepository.getMockLessons()
        }
    }
}