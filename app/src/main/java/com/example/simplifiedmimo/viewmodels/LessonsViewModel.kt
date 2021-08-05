package com.example.simplifiedmimo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplifiedmimo.data.LessonsRepository
import com.example.simplifiedmimo.data.api.models.Lesson
import com.example.simplifiedmimo.data.database.models.LessonCompletionEvent
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
            // for testing the app with more lessons, the following line of code can be used and the above one can be commented out instead
            // _lessons.value = lessonsRepository.getMockLessons()
        }
    }

    fun insertNewLessonCompletionEvent(lessonCompletionEvent: LessonCompletionEvent) {
        viewModelScope.launch {
            // ask the repository to insert a new lesson completion event record into the database
            lessonsRepository.insertNewLessonCompletionEvent(lessonCompletionEvent)
        }
    }
}