package com.example.tracnghiem.activity.main.course.lesson

import androidx.lifecycle.MutableLiveData
import com.example.tracnghiem.R
import com.example.tracnghiem.adapter.QuizAdapter
import com.example.tracnghiem.base.BaseViewModel
import com.example.tracnghiem.data.model.Content
import com.example.tracnghiem.data.model.Lesson
import com.google.firebase.firestore.DocumentReference

class CourseLessonViewModel : BaseViewModel( ) {
    private var mListLessonLiveData = CourseLessonLiveData()
    private val mAdapter = QuizAdapter(this, R.layout.item_course_lesson)
    // a nameLesson and ListQuestion
    private var mListSelectedLiveData = MutableLiveData<Pair<String?,ArrayList<DocumentReference>?>> ()
    fun getAdapter() = mAdapter

    private val mListLesson = mutableListOf<Lesson>()
    private var mMosId: String? = ""

    // call from fragment
    fun setMosId(mosId: String?) {
        mMosId = mosId
    }
    fun getListChapterSelectedLiveData()= mListSelectedLiveData
    fun requestListLesson() = mListLessonLiveData.fetchData(mMosId)
    fun getListLessonLiveData() = mListLessonLiveData.getLessonLiveData()


    fun getLessonAt(position: Int): Lesson {
        return mListLesson[position]
    }


    fun updateData(listContent: MutableList<Lesson>) {
        mListLesson.clear()
        mListLesson.addAll(listContent)
        mAdapter.loadData(mListLesson.size)
    }

    fun onLessonClick(position: Int) {
        val data = Pair(mListLesson[position].nameLesson,mListLesson[position].questions)
        mListSelectedLiveData.value = data
    }
    override fun onError(error: Throwable) {
        //do some thing
    }
}