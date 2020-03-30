package com.example.tracnghiem.activity.main.course.chapter

import androidx.lifecycle.MutableLiveData
import com.example.tracnghiem.R
import com.example.tracnghiem.adapter.QuizAdapter
import com.example.tracnghiem.base.BaseViewModel
import com.example.tracnghiem.data.model.Content

class CourseChapterViewModel : BaseViewModel() {
    private var mListChapterLiveData = CourseChapterLiveData()
    private val mAdapter = QuizAdapter(this, R.layout.item_course_chapter)
    private var mListSelectedLiveData = MutableLiveData<Pair<String?,String?>>()
    fun getAdapter() = mAdapter

    private val mListContent = mutableListOf<Content>()
    private var mMosId: String? = ""

    // call from fragment
    fun setMosId(mosId: String?) {
        mMosId = mosId
    }
    fun getListChapterSelectedLiveData()= mListSelectedLiveData
    fun requestListChapter() = mListChapterLiveData.fetchData(mMosId)
    fun getListChapterLiveData() = mListChapterLiveData.getChaptersLiveData()


    fun getChapterAt(position: Int): Content {
        return mListContent[position]
    }


    fun updateData(listContent: MutableList<Content>) {
        mListContent.clear()
        mListContent.addAll(listContent)
        mAdapter.loadData(mListContent.size)
    }

    fun onChapterClick(position: Int) {
        val data = Pair(mListContent[position].data,mListContent[position].title)
        mListSelectedLiveData.value = data
    }
    override fun onError(error: Throwable) {
        //do some thing
    }
}