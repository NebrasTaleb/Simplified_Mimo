package com.example.simplifiedmimo.data.api

import com.example.simplifiedmimo.data.api.models.LessonsApiResponse
import retrofit2.http.GET

interface LessonsApi {
    @GET("lessons")
    suspend fun getLessons(): LessonsApiResponse
}