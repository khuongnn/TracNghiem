package com.example.tracnghiem.data.room

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.tracnghiem.data.model.Question

class QuizRepository(application: Application) {
    private val quizDao: QuizDao
    private val allQuizs: LiveData<List<Question>>

    init {
        val database: QuizDatabase = QuizDatabase.getInstance(
            application.applicationContext
        )!!
        quizDao = database.quizDao()
        allQuizs = quizDao.getAllQuestions()
    }

    fun insert(question: Question) {
        // async to insert
        val insertQuizAsyncTask = InsertQuizAsyncTask(quizDao).execute(question)
    }

    fun update(question: Question) {
        val updateQuizAsyncTask = UpdateQuizAsyncTask(quizDao).execute(question)
    }

    fun delete(question: Question) {
        val deleteQuizAsyncTask = DeleteQuizAsyncTask(quizDao).execute(question)
    }

    fun deleteAll(question: Question) {
        val deleteAllQuizAsyncTask = DeleteAllQuizAsyncTask(quizDao).execute()
    }

    fun getAllNotes(): LiveData<List<Question>> {
        return allQuizs
    }


    companion object {
        private class InsertQuizAsyncTask(val quizDao: QuizDao) :
            AsyncTask<Question, Unit, Unit>() {
            override fun doInBackground(vararg params: Question?) {
                quizDao.insert(params[0]!!)
            }
        }

        private class UpdateQuizAsyncTask(val quizDao: QuizDao) :
            AsyncTask<Question, Unit, Unit>() {
            override fun doInBackground(vararg params: Question?) {
                quizDao.update(params[0]!!)
            }
        }

        private class DeleteQuizAsyncTask(val quizDao: QuizDao) :
            AsyncTask<Question, Unit, Unit>() {
            override fun doInBackground(vararg params: Question?) {
                quizDao.delete(params[0]!!)
            }
        }

        private class DeleteAllQuizAsyncTask(val quizDao: QuizDao) :
            AsyncTask<Question, Unit, Unit>() {
            override fun doInBackground(vararg params: Question?) {
                quizDao.deleteAllQuestion()
            }

        }
    }
}