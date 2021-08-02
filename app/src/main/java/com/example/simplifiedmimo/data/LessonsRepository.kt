package com.example.simplifiedmimo.data

import android.widget.Toast
import com.example.simplifiedmimo.App
import com.example.voucherprocessor.services.LessonsService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LessonsRepository {

    fun getLessons() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = LessonsService.lessonsApi.getLessons()
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    App.appContext,
                    "the lessons has been fetched successfully!  ${response.lessons.size}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}