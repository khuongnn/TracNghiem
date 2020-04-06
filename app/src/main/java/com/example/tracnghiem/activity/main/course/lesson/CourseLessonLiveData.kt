package com.example.tracnghiem.activity.main.course.lesson

import androidx.lifecycle.MutableLiveData
import com.example.tracnghiem.data.model.Lesson
import com.example.tracnghiem.data.model.Mos
import com.example.tracnghiem.event.ShowNetworkErrorDialogEvent
import com.example.tracnghiem.network.NetworkManager
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber
import kotlin.system.measureTimeMillis

class CourseLessonLiveData  : KoinComponent {
    private val tag = javaClass.name
    private val networkManager by inject<NetworkManager>()
    private val mLessonLiveData = MutableLiveData<ArrayList<Lesson>?>()
    fun getLessonLiveData() = mLessonLiveData

    fun fetchData(mosId: String?) {
        if (!networkManager.isNetworkConnected()) {
            EventBus.getDefault().post(ShowNetworkErrorDialogEvent())
            mLessonLiveData.value = null
            return
        }
        if (mosId == null) {
            mLessonLiveData.value = null
            return
        }


        val time = measureTimeMillis {
            CoroutineScope(Dispatchers.IO).launch {
                val doc = getLessonContent(mosId)
                try {
                    val mos = doc.toObject(Mos::class.java)

                    if (mos?.lesson == null || mos.lesson?.isEmpty() == true) {
                        setValueOnMainThread(null)
                    } else {
                        val listLesson = ArrayList<Lesson>()
                        mos.lesson?.forEach { docRef ->
                            val lessonRef = getLesson(docRef)
                            try {
                                val lesson = lessonRef.toObject(Lesson::class.java)
                                lesson?.id = lessonRef.id
                                lesson?.let { listLesson.add(it) }

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                        setValueOnMainThread(listLesson)
                    }

                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
        Timber.tag(tag).d("Task take $time .ms to done.........")
    }

    private suspend fun setValueOnMainThread(value: ArrayList<Lesson>?) {
        withContext(Dispatchers.Main) {
            mLessonLiveData.value = value
        }
    }

    private suspend fun getLessonContent(mosId: String) =
        FirebaseFirestore.getInstance()
            .collection("mos")
            .document(mosId)
            .get().await()

    private suspend fun getLesson(documentReference: DocumentReference) =
        documentReference.get().await()

}