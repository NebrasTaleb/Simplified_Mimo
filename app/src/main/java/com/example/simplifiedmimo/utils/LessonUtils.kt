package com.example.simplifiedmimo.utils

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import com.example.simplifiedmimo.data.api.models.Content
import com.example.simplifiedmimo.data.api.models.Lesson
import com.example.simplifiedmimo.data.api.models.contentsLength

object LessonUtils {

    // this function takes a lesson object as an input parameter and returns its contents text formatted (colored properly)
    fun getLessonContentsFormatted(lesson: Lesson): SpannableStringBuilder {
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

    // this function takes a lesson content object as an input parameter and returns its text formatted (colored properly)
    fun getLessonContentFormatted(lessonContent: Content):SpannableStringBuilder{
        // create a spannable string builder with the text of the content
        val spannable = SpannableStringBuilder(lessonContent.text)
            // color the text of this content with its color
            spannable.setSpan(
                ForegroundColorSpan(Color.parseColor(lessonContent.color)),
                0, // the start index of span to be colored
                lessonContent.text.length, // the end index
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        return spannable
    }

    // this function detects and returns the order (the position) of the input within the contents of a lesson
    fun detectTheInputOrder(lesson: Lesson): LessonInputOrder {
        val firstContentTextLength = lesson.contents.first().text.length
        val lastContentTextLength = lesson.contents.last().text.length
        val indexOfFirstLetterInLastContentText = lesson.contentsLength() - lastContentTextLength
        return when {
            lesson.input!!.startIndex < firstContentTextLength -> LessonInputOrder.BEGINNING
            lesson.input.startIndex > indexOfFirstLetterInLastContentText -> LessonInputOrder.END
            else -> LessonInputOrder.MIDDLE
        }
    }
}