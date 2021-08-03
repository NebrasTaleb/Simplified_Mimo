package com.example.simplifiedmimo.data

import com.example.simplifiedmimo.data.api.HttpClient
import com.example.simplifiedmimo.data.api.models.Lesson

class LessonsRepository {

    suspend fun getLessons(): List<Lesson> {
        // call the lessons api and get the lessons
        val response = HttpClient.lessonsApi.getLessons()
        return response.lessons
    }
}