package com.example.simplifiedmimo.utils

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import com.example.simplifiedmimo.data.api.models.Lesson

object LessonUtils {

    // this function takes a lesson object as an input parameter and returns its content formatted (colored properly)
    fun getLessonContentFormatted(lesson: Lesson): SpannableStringBuilder {
        // the following variable is used to save the current length of the so far formatted contents
        var currentContentLength = 0
        // create an empty spannable string builder
        val spannable = SpannableStringBuilder("")
        // for each content in the lesson
        for (content in lesson.contents) {
            // add the text of the content to the spannable
            spannable.append(content.text)
            // color the text of this content with its color
            spannable.setSpan(
                ForegroundColorSpan(Color.parseColor(content.color)),
                currentContentLength, // the start index of span to be colored
                currentContentLength + content.text.length, // the end index
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            // increase currentContentLength so the start index of the span is correct in the next iteration
            currentContentLength += content.text.length
        }
        return spannable
    }
}