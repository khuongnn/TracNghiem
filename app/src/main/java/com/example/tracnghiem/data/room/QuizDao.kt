package com.example.tracnghiem.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tracnghiem.data.model.Question

@Dao
interface QuizDao {
    @Insert
    fun insert(question: Question)

    @Update
    fun update(question: Question)

    @Delete
    fun delete(question: Question)

    @Query("DELETE FROM question_table")
    fun deleteAllQuestion()

    @Query("SELECT * FROM question_table ORDER BY priority DESC")
    fun getAllQuestions(): LiveData<List<Question>>
}