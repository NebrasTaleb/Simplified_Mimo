package com.example.voucherprocessor.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var API_BASE_URL = "https://mimochallenge.azurewebsites.net/api/"

object LessonsService {
    var httpClient = OkHttpClient.Builder()

    var builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create()
        )

    var retrofit: Retrofit = builder
        .client(httpClient.build())
        .build()
    val lessonsApi: LessonsApi = retrofit.create(LessonsApi::class.java)
}