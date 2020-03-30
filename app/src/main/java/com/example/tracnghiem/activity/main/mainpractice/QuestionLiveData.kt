package com.example.tracnghiem.activity.main.mainpractice

import androidx.lifecycle.MutableLiveData
import com.example.tracnghiem.data.model.Questions
import com.example.tracnghiem.network.NetworkManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class QuestionLiveData : KoinComponent {
    private val tag = javaClass.name
    private val networkManager by inject<NetworkManager>()
    private val mListQuestions = MutableLiveData<ArrayList<Questions>?>()
    fun getListQuestions() = mListQuestions

    fun fetchList() {
        if (!networkManager.isNetworkConnected()) {
            mListQuestions.value = null
            return
        }

        FirebaseFirestore.getInstance()
            .collection("questions")
            .orderBy("position", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result ->
                val listQuestions = ArrayList<Questions>()

                for (document in result) {
                    try {
                        val questions = document.toObject(Questions::class.java)
                        questions.id = document.id
                        listQuestions.add(questions)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                mListQuestions.value = listQuestions

            }.addOnFailureListener {
                mListQuestions.value = null
                Timber.tag(tag).d(it.localizedMessage)
            }
    }

}