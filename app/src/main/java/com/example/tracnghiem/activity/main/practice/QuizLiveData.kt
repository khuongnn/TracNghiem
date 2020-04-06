package com.example.tracnghiem.activity.main.practice

import androidx.lifecycle.MutableLiveData
import com.example.tracnghiem.data.model.QuizDetail
import com.example.tracnghiem.event.ShowNetworkErrorDialogEvent
import com.example.tracnghiem.network.NetworkManager
import com.example.tracnghiem.utils.FirestoreKey
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import org.greenrobot.eventbus.EventBus
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class QuizLiveData : KoinComponent {
    private var tag = javaClass.name
    private val networkManager by inject<NetworkManager>()
    private var mListQuizDetail = MutableLiveData<ArrayList<QuizDetail>?>()

    fun getQuizList() = mListQuizDetail

    fun fetchList() {
        if (!networkManager.isNetworkConnected()) {
            EventBus.getDefault().post(ShowNetworkErrorDialogEvent())
            mListQuizDetail.value = null
            return
        }
        FirebaseFirestore.getInstance()
            .collection(FirestoreKey.QUIZ_QUIZ_DETAIL)
            .orderBy("position", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result ->
                val listQuizDetail = ArrayList<QuizDetail>()

                for (document in result) {
                    try {
                        val quizDetail = document.toObject(QuizDetail::class.java)
                        quizDetail.id = document.id
                        listQuizDetail.add(quizDetail)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                mListQuizDetail.value = listQuizDetail
            }
            .addOnFailureListener{
                mListQuizDetail.value = null
                Timber.tag(tag).d(it.localizedMessage)
            }
    }
}