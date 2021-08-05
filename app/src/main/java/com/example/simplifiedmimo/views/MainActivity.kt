package com.example.simplifiedmimo.views

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.simplifiedmimo.data.api.models.Lesson
import com.example.simplifiedmimo.data.api.models.hasInteraction
import com.example.simplifiedmimo.data.database.models.LessonCompletionEvent
import com.example.simplifiedmimo.databinding.ActivityMainBinding
import com.example.simplifiedmimo.utils.LessonInputOrder
import com.example.simplifiedmimo.utils.LessonUtils
import com.example.simplifiedmimo.viewmodels.LessonsViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val lessonsViewModel: LessonsViewModel by viewModels()
    private lateinit var fetchedLessons: List<Lesson>
    private var currentDisplayedLessonIndex = 0
    private lateinit var currentDisplayedLesson: Lesson
    private lateinit var currentCorrectAnswer: String
    private lateinit var currentLessonStartingTimestamp: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // observe the list of lessons live data from the view model
        lessonsViewModel.lessons.observe(this) {
            // get the the lessons list when it is ready
            fetchedLessons = it
            // assign the first lesson as the current displayed lesson
            currentDisplayedLesson = fetchedLessons.first()
            // show the lesson to the user with taking the interaction availability into consideration
            showLesson()
            // after showing the first lesson, save the starting timestamp of it in currentLessonStartingTimestamp
            currentLessonStartingTimestamp = Date().toString()
        }
        binding.solveLessonButton.setOnClickListener {
            // reset the views
            resetViews()
            // insert a new lesson completion record into the database
            insertNewLessonCompletionEvent()
            // update the currentLessonStartingTimestamp so the starting timestamp of the next lesson is saved correctly
            currentLessonStartingTimestamp = Date().toString()
            // proceed to the next lesson or finish if no lessons remained
            proceedToNextLessonOrFinish()
        }
        binding.inputEditText.doAfterTextChanged {
            // whenever the text inside the input field is changed, check it if it's equal to the current correct answer or not
            // ("it.toString()" is the current text inside the input field)
            // if they are equal to each other, then enable the Next button so the user can proceed to the next lesson
            binding.solveLessonButton.isEnabled = it.toString() == currentCorrectAnswer
        }
    }

    private fun proceedToNextLessonOrFinish() {
        // check if the current displayed lesson is the last lesson
        if (currentDisplayedLessonIndex == fetchedLessons.size - 1) {
            // it's the last lesson so show done layout
            showLessonsDoneLayout()
        } else {
            // it's not the last lesson
            currentDisplayedLessonIndex++
            // so get the next one
            currentDisplayedLesson = fetchedLessons[currentDisplayedLessonIndex]
            // and show it
            showLesson()
        }
    }

    private fun showLesson() {
        // before showing the lesson, check if it has an interaction (an input)
        if (currentDisplayedLesson.hasInteraction()) {
            showLessonWithInteraction()
        } else {
            showLessonWithoutInteraction()
        }
    }

    private fun showLessonWithoutInteraction() {
        // get the contents of the lesson formatted and assign them to the start text view
        val lessonContentsFormatted = LessonUtils.getLessonContentsFormatted(currentDisplayedLesson)
        binding.startTextView.text = lessonContentsFormatted
    }

    private fun showLessonWithInteraction() {
        // disable the Next button for lessons with interaction
        binding.solveLessonButton.isEnabled = false
        // show the input field
        binding.inputEditText.visibility = View.VISIBLE
        // detect the order of the input and act accordingly
        when (LessonUtils.detectTheInputOrder(currentDisplayedLesson)) {
            LessonInputOrder.BEGINNING -> setInputAtTheBeginning()
            LessonInputOrder.MIDDLE -> setInputAtTheMiddle()
            LessonInputOrder.END -> setInputAtTheEnd()
        }
    }

    private fun setInputAtTheBeginning() {
        // get the input content of the lesson (which is the first content in the lesson)
        val inputContent = currentDisplayedLesson.contents.first()
        // color the text of the input field
        binding.inputEditText.setTextColor(Color.parseColor(inputContent.color))
        // save in currentCorrectAnswer the answer that the user should enter in the input field
        currentCorrectAnswer = inputContent.text
        val formattedContent = SpannableStringBuilder("")
        // get the text of all the other contents (all except the first one) formatted
        for (index in 1 until currentDisplayedLesson.contents.size) {
            val content = currentDisplayedLesson.contents[index]
            formattedContent.append(LessonUtils.getLessonContentFormatted(content))
        }
        // set this gathered and formatted contents text to the end text view so that the input field appears at the beginning
        binding.endTextView.text = formattedContent
    }

    private fun setInputAtTheMiddle() {
        // get the input content of the lesson (which is the middle content in the lesson)
        // NOTE: for simplicity, I assumed that there are always 3 contents in the lesson, so the index of the middle one is 1
        val inputContent = currentDisplayedLesson.contents[1]
        // color the text of the input field
        binding.inputEditText.setTextColor(Color.parseColor(inputContent.color))
        // save in currentCorrectAnswer the answer that the user should enter in the input field
        currentCorrectAnswer = inputContent.text
        // get the text of the first content formatted
        val startFormattedContent =
            LessonUtils.getLessonContentFormatted(currentDisplayedLesson.contents.first())
        // get the text of the last content formatted
        val endFormattedContent =
            LessonUtils.getLessonContentFormatted(currentDisplayedLesson.contents.last())
        // set each formatted content text to its text view so that the input field appears in the middle
        binding.startTextView.text = startFormattedContent
        binding.endTextView.text = endFormattedContent
    }

    private fun setInputAtTheEnd() {
        // get the input content of the lesson (which is the last content in the lesson)
        val inputContent = currentDisplayedLesson.contents.last()
        // color the text of the input field
        binding.inputEditText.setTextColor(Color.parseColor(inputContent.color))
        // save in currentCorrectAnswer the answer that the user should enter in the input field
        currentCorrectAnswer = inputContent.text
        val formattedContent = SpannableStringBuilder("")
        // get the text of all the other contents (all except the last one) formatted
        for (index in 0 until currentDisplayedLesson.contents.size - 1) {
            val content = currentDisplayedLesson.contents[index]
            formattedContent.append(LessonUtils.getLessonContentFormatted(content))
        }
        // set this gathered and formatted contents text to the start text view so that the input field appears at the end
        binding.startTextView.text = formattedContent
    }

    private fun showLessonsDoneLayout() {
        binding.lessonsContentsLayout.visibility = View.GONE
        binding.solveLessonButton.visibility = View.GONE
        binding.lessonsDoneLayout.visibility = View.VISIBLE
    }

    private fun resetViews() {
        // this function just rests the text views and the input field so
        // the data of the previous lesson don't appear in the next lesson
        binding.startTextView.text = ""
        binding.endTextView.text = ""
        binding.inputEditText.text.clear()
    }

    private fun insertNewLessonCompletionEvent() {
        // create a lesson completion event object and insert it into the database
        val lessonCompletionEvent = LessonCompletionEvent(
            lessonId = currentDisplayedLesson.id,
            startingTimestamp = currentLessonStartingTimestamp,
            completionTimestamp = Date().toString()
        )
        lessonsViewModel.insertNewLessonCompletionEvent(lessonCompletionEvent)
    }
}