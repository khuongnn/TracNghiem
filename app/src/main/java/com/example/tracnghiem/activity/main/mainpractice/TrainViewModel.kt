package com.example.tracnghiem.activity.main.mainpractice

import com.example.tracnghiem.base.BaseViewModel

class TrainViewModel : BaseViewModel() {
    private val mListQuestions = QuestionLiveData()

    fun requestListQuestion() = mListQuestions.fetchList()
    fun getListQuestionLiveData() = mListQuestions.getListQuestions()


    override fun onError(error: Throwable) {
        // do no thing
    }

}