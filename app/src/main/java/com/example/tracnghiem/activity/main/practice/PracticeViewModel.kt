package com.example.tracnghiem.activity.main.practice

import androidx.lifecycle.MutableLiveData
import com.example.tracnghiem.R
import com.example.tracnghiem.adapter.QuizAdapter
import com.example.tracnghiem.base.BaseViewModel
import com.example.tracnghiem.data.model.QuizDetail

class PracticeViewModel: BaseViewModel(){

    private var mListQuizLiveData = QuizLiveData()
    private val mAdapter = QuizAdapter(this, R.layout.item_quiz)

    fun getAdapter() = mAdapter
    private val mListQuizDetail = mutableListOf<QuizDetail>()

    // Quiz selected
    private val mQuizSelected = MutableLiveData<Pair<String?, String?>>()
    fun getQuizSelectedLiveData() = mQuizSelected

    fun requestListQuizDetail() {
        mListQuizLiveData.fetchList()
    }

    fun getListQuizLiveData() = mListQuizLiveData.getQuizList()

    fun updateData(listQuizDetail: MutableList<QuizDetail>) {
        mListQuizDetail.clear()
        mListQuizDetail.addAll(listQuizDetail)
        mAdapter.loadData(mListQuizDetail.size)
    }

    fun getQuizDetailAt(position: Int): QuizDetail {
        return mListQuizDetail[position]
    }

    fun onQuizClick(position: Int) {
        // set data to handler
        val quizDetail = mListQuizDetail[position]
        mQuizSelected.value = Pair(quizDetail.id, quizDetail.quizName)
    }

    override fun onError(error: Throwable) {
        error.printStackTrace()
    }
}