package com.example.tracnghiem.activity.main.mainpractice.fragment

import android.os.Bundle
import android.view.View
import com.example.tracnghiem.R
import com.example.tracnghiem.base.BaseFragment
import com.example.tracnghiem.data.model.Questions
import com.example.tracnghiem.databinding.FragmentSlidePageBinding
import kotlinx.android.synthetic.main.fragment_slide_page.*
import kotlinx.android.synthetic.main.item_question.*

class PracticeQuizFragment() : BaseFragment<FragmentSlidePageBinding>() {
    override fun setLayoutId(): Int = R.layout.fragment_slide_page
    private var mListQuestion = ArrayList<Questions>()
    private var mPagePosition: Int = 0



    override fun initViewModel() {
    }

    override fun initData(data: Bundle?) {
        data?.let {
            val mData = data.getSerializable("DATA")
            mPagePosition = data.getInt("POSITION")
            mListQuestion = mData as ArrayList<Questions>
            try {
                tvQuestion.text= mListQuestion[mPagePosition].question
                tvNumber.text= "Câu "+ mListQuestion[mPagePosition].position
                radA.text = mListQuestion[mPagePosition].a
                radB.text = mListQuestion[mPagePosition].b
                radC.text = mListQuestion[mPagePosition].c
                radD.text = mListQuestion[mPagePosition].d
            }catch (ex : Exception){
                content.visibility= View.GONE
            }

        }


    }

    override fun iniListener() {

    }


}