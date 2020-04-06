package com.example.tracnghiem.activity.main.course

import androidx.lifecycle.MutableLiveData
import com.example.tracnghiem.data.model.Mos
import com.example.tracnghiem.event.ShowNetworkErrorDialogEvent
import com.example.tracnghiem.network.NetworkManager
import com.example.tracnghiem.utils.FirestoreKey
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import org.greenrobot.eventbus.EventBus
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber
import java.lang.Exception

class CourseLiveData : KoinComponent {
    private var tag = javaClass.name
    private val networkManager by inject<NetworkManager>()
    private var mListMosDetail = MutableLiveData<ArrayList<Mos>?>()
    fun getMosDetail() = mListMosDetail

    fun fetchList() {
        if (!networkManager.isNetworkConnected()){
            EventBus.getDefault().post(ShowNetworkErrorDialogEvent())
            mListMosDetail.value = null
            return
        }
        FirebaseFirestore.getInstance()
            .collection(FirestoreKey.QUIZ_MOS)
            .orderBy("position", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result->
                val listMosDetail = ArrayList<Mos>()
                for (document in result) {
                    try {
                        val mosDetail = document.toObject(Mos::class.java)
                        mosDetail.id = document.id
                        listMosDetail.add(mosDetail)
                    }
                    catch (e : Exception){
                        e.printStackTrace()
                    }
                }
                mListMosDetail.value = listMosDetail
            }
            .addOnFailureListener{
                mListMosDetail.value = null
                Timber.tag(tag).d(it.localizedMessage)
            }


    }
}