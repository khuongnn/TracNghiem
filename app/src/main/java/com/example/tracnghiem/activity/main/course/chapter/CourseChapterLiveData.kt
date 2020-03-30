package com.example.tracnghiem.activity.main.course.chapter

import androidx.lifecycle.MutableLiveData
import com.example.tracnghiem.data.model.Content
import com.example.tracnghiem.data.model.Mos
import com.example.tracnghiem.network.NetworkManager
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber
import kotlin.system.measureTimeMillis

class CourseChapterLiveData : KoinComponent {
    private val tag = javaClass.name
    private val networkManager by inject<NetworkManager>()
    private val mChapterLiveData = MutableLiveData<ArrayList<Content>?>()
    fun getChaptersLiveData() = mChapterLiveData

    fun fetchData(mosId: String?) {
        if (!networkManager.isNetworkConnected()) {
            mChapterLiveData.value = null
            return
        }
        if (mosId == null) {
            mChapterLiveData.value = null
            return
        }

        val time = measureTimeMillis {
            CoroutineScope(IO).launch {
                val doc = getChapterContent(mosId)
                try {
                    val mos = doc.toObject(Mos::class.java)

                    if (mos?.content == null || mos.content?.isEmpty() == true) {
                        setValueOnMainThread(null)
                    } else {
                        val listContent = ArrayList<Content>()
                        mos.content?.forEach { docRef ->
                            val contentRef = getChapter(docRef)
                            try {
                                val content = contentRef.toObject(Content::class.java)
                                content?.id = contentRef.id
                                content?.let { listContent.add(it) }

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                        setValueOnMainThread(listContent)
                    }

                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
        Timber.tag(tag).d("Task take $time .ms to done.........")
    }

    private suspend fun setValueOnMainThread(value: ArrayList<Content>?) {
        withContext(Main) {
            mChapterLiveData.value = value
        }
    }

    private suspend fun getChapterContent(mosId: String) =
        FirebaseFirestore.getInstance()
            .collection("mos")
            .document(mosId)
            .get().await()

    private suspend fun getChapter(documentReference: DocumentReference) =
        documentReference.get().await()
}