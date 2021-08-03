package com.example.simplifiedmimo.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private var API_BASE_URL = "https://mimochallenge.azurewebsites.net/api/"

object HttpClient {
    private var httpClient = OkHttpClient.Builder()

    private var builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create()
        )

    private var retrofit: Retrofit = builder
        .client(httpClient.build())
        .build()
    val lessonsApi: LessonsApi = retrofit.create(LessonsApi::class.java)
}