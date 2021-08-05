package com.example.simplifiedmimo.data.database

import androidx.room.Room
import com.example.simplifiedmimo.App

object DatabaseService {
    private val db = Room.databaseBuilder(
        App.appContext,
        AppDatabase::class.java, "simplified-mimo-database"
    ).build()

    val lessonCompletionEventDao = db.lessonCompletionEventDao()
}