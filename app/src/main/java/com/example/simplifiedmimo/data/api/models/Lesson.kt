package com.example.simplifiedmimo.data.api.models

import com.google.gson.annotations.SerializedName

data class Lesson(
    val id: Int,
    @SerializedName("content")
    val contents: List<Content>,
    val input: Input?
)

fun Lesson.hasInteraction(): Boolean {
    return this.input != null
}

// this extension function returns the text length of all contents in a lesson
fun Lesson.contentsLength(): Int {
    var lengthOfAllContents = 0
    for (content in this.contents) {
        lengthOfAllContents += content.text.length
    }
    return lengthOfAllContents
}