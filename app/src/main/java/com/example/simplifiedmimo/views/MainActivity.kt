package com.example.simplifiedmimo.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.simplifiedmimo.databinding.ActivityMainBinding
import com.example.simplifiedmimo.utils.LessonUtils
import com.example.simplifiedmimo.viewmodels.LessonsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val lessonsViewModel: LessonsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // observe the list of lessons live data from the view model
        lessonsViewModel.lessons.observe(this) {
            // get the the lessons list when it is ready
            val lessons = it
            // get the content of the first lesson formatted
            val firstLesson = LessonUtils.getLessonContentFormatted(lessons[0])
            // assign the content of the first lesson to the start text view
            binding.startTextView.text = firstLesson
        }
    }
}