package com.example.simplifiedmimo.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.simplifiedmimo.data.database.models.LessonCompletionEvent

@Dao
interface LessonCompletionEventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessonCompletionEvent(lessonCompletionEvent: LessonCompletionEvent)
}