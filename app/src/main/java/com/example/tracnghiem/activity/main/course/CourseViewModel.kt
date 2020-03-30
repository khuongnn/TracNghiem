package com.example.tracnghiem.activity.main.course

import androidx.lifecycle.MutableLiveData
import com.example.tracnghiem.R
import com.example.tracnghiem.adapter.QuizAdapter
import com.example.tracnghiem.base.BaseViewModel
import com.example.tracnghiem.data.model.Mos
import java.text.FieldPosition

class CourseViewModel : BaseViewModel() {
    private var mListCourseLiveData = CourseLiveData()

    private var mAdapter = QuizAdapter(this, R.layout.item_course)
    fun getAdapter() = mAdapter

    private val mListMosDetail = mutableListOf<Mos>()
    // Mos Selected
    private var mMosSelected = MutableLiveData<Pair<String?,String?>> ()
    fun getMosSelectedLiveData() = mMosSelected

    // call from fragment
    fun requestListMosDetail() = mListCourseLiveData.fetchList()
    fun getListMosLiveData () = mListCourseLiveData.getMosDetail()

    fun updateData(listMos : MutableList<Mos>){
        mListMosDetail.clear()
        mListMosDetail.addAll(listMos)
        mAdapter.loadData(mListMosDetail.size)
    }


    fun getMosDetailAt (position: Int) : Mos {
        return mListMosDetail[position]
    }
    fun onMosClick(position: Int){
        val mosDetail = mListMosDetail[position]
        mMosSelected.value = Pair(mosDetail.id,mosDetail.nameMos)
    }

    override fun onError(error: Throwable) {
        error.printStackTrace()
    }
}