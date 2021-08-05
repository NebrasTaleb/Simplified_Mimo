package com.example.simplifiedmimo.data

import com.example.simplifiedmimo.data.api.HttpClient
import com.example.simplifiedmimo.data.api.models.Lesson
import com.example.simplifiedmimo.data.api.models.LessonsApiResponse
import com.example.simplifiedmimo.data.database.DatabaseService
import com.example.simplifiedmimo.data.database.models.LessonCompletionEvent
import com.example.simplifiedmimo.utils.MockLessonsData
import com.google.gson.Gson

class LessonsRepository {

    suspend fun getLessons(): List<Lesson> {
        // call the lessons api and get the lessons
        val response = HttpClient.lessonsApi.getLessons()
        return response.lessons
    }

    suspend fun insertNewLessonCompletionEvent(lessonCompletionEvent: LessonCompletionEvent){
        // call the database service to insert a new lesson completion event record into the database
        DatabaseService.lessonCompletionEventDao.insertLessonCompletionEvent(lessonCompletionEvent)
    }

    fun getMockLessons(): List<Lesson> {
        // this function returns a mock lessons data for testing
        val jsonHandler = Gson()
        val mockJsonData = MockLessonsData.getLessons()
        val mockLessonsApiResponse = jsonHandler.fromJson(mockJsonData, LessonsApiResponse::class.java)
        return mockLessonsApiResponse.lessons
    }
}