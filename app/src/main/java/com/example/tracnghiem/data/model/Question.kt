package com.example.tracnghiem.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "question_table")
data class Question(
    var title: String,
    var description: String,
    var priority: Int
) {
    //does it matter if these are private or not?
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}