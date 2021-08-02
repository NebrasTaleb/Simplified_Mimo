package com.example.voucherprocessor.services

import com.example.simplifiedmimo.services.api.models.LessonsApiResponse
import retrofit2.http.GET

interface LessonsApi {
    @GET("lessons")
    suspend fun getLessons(): LessonsApiResponse
}