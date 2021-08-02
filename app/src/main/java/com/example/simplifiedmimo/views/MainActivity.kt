package com.example.simplifiedmimo.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.simplifiedmimo.R
import com.example.simplifiedmimo.viewmodels.LessonsViewModel

class MainActivity : AppCompatActivity() {

    private val lessonsViewModel : LessonsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lessonsViewModel.getLessons()
    }
}