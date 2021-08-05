package com.example.simplifiedmimo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.simplifiedmimo.data.database.models.LessonCompletionEvent

@Database(entities = [LessonCompletionEvent::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lessonCompletionEventDao(): LessonCompletionEventDao
}