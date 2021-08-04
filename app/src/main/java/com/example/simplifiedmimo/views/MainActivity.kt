package com.example.simplifiedmimo.views

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.simplifiedmimo.data.api.models.Lesson
import com.example.simplifiedmimo.data.api.models.hasInteraction
import com.example.simplifiedmimo.databinding.ActivityMainBinding
import com.example.simplifiedmimo.utils.LessonInputOrder
import com.example.simplifiedmimo.utils.LessonUtils
import com.example.simplifiedmimo.viewmodels.LessonsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val lessonsViewModel: LessonsViewModel by viewModels()
    private lateinit var fetchedLessons: List<Lesson>
    private var currentDisplayedLessonIndex = 0
    private lateinit var currentDisplayedLesson: Lesson

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
        }
        binding.solveLessonButton.setOnClickListener {
            // reset the views
            resetViews()
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
        // get the text color of the input content (which is the first content in the lesson)
        val inputTextColor = currentDisplayedLesson.contents.first().color
        // color the text of the input field
        binding.inputEditText.setTextColor(Color.parseColor(inputTextColor))
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
        // get the text color of the input content (which is the middle content in the lesson)
        // NOTE: for simplicity, I assumed that there are always 3 contents in the lesson, so the index of the middle one is 1
        val inputTextColor = currentDisplayedLesson.contents[1].color
        // color the text of the input field
        binding.inputEditText.setTextColor(Color.parseColor(inputTextColor))
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
        // get the text color of the input content (which is the last content in the lesson)
        val inputTextColor = currentDisplayedLesson.contents.last().color
        // color the text of the input field
        binding.inputEditText.setTextColor(Color.parseColor(inputTextColor))
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
}