package com.example.tracnghiem.data.room

import android.os.AsyncTask
import com.example.tracnghiem.data.model.Question
// add data fist
class PopulateDbAsyncTask(db: QuizDatabase?): AsyncTask<Unit, Unit, Unit>(){
    private val quizDao = db?.quizDao()
    override fun doInBackground(vararg params: Unit?) {
        quizDao?.insert(Question("title 1", "description 1", 1))
        quizDao?.insert(Question("title 2", "description 2", 2))
        quizDao?.insert(Question("title 3", "description 3", 3))
        quizDao?.insert(Question("title 4", "description 1", 4))
    }
}