package com.example.tracnghiem.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.tracnghiem.data.model.Question

@Database(entities = [Question::class], version = 1)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun quizDao(): QuizDao

    companion object {
        private var instance: QuizDatabase? = null
        fun getInstance(context: Context): QuizDatabase? {
            if (instance == null) {
                synchronized(QuizDatabase::class) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            QuizDatabase::class.java,
                            "quiz_database"
                        ).fallbackToDestructiveMigration()
                        .addCallback(roomCallBack)
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        //add data fist
        private val roomCallBack = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()

            }

        }
    }
}