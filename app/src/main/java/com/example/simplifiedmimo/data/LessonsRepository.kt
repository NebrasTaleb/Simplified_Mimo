package com.example.simplifiedmimo.data

import com.example.simplifiedmimo.data.api.HttpClient
import com.example.simplifiedmimo.data.api.models.Lesson
import com.example.simplifiedmimo.data.api.models.LessonsApiResponse
import com.example.simplifiedmimo.utils.MockLessonsData
import com.google.gson.Gson

class LessonsRepository {

    suspend fun getLessons(): List<Lesson> {
        // call the lessons api and get the lessons
        val response = HttpClient.lessonsApi.getLessons()
        return response.lessons
    }

    fun getMockLessons(): List<Lesson> {
        // this function returns a mock lessons data for testing
        val jsonHandler = Gson()
        val mockJsonData = MockLessonsData.getLessons()
        val mockLessonsApiResponse = jsonHandler.fromJson(mockJsonData, LessonsApiResponse::class.java)
        return mockLessonsApiResponse.lessons
    }
}