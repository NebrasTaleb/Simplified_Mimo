package com.example.simplifiedmimo.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons_completion_events")
data class LessonCompletionEvent(
    @PrimaryKey val lessonId: Int,
    @ColumnInfo(name = "Starting_Timestamp") val startingTimestamp: String,
    @ColumnInfo(name = "Completion_Timestamp") val completionTimestamp: String,
)