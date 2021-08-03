package com.example.simplifiedmimo.data.api.models

import com.google.gson.annotations.SerializedName

data class Lesson(
    val id: Int,
    @SerializedName("content")
    val contents: List<Content>,
    val input: Input?
)