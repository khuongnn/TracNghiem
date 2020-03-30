package com.example.tracnghiem.activity.main.course.lesson

import android.os.Bundle
import android.view.View
import com.example.tracnghiem.R
import com.example.tracnghiem.base.BaseFragment
import com.example.tracnghiem.base.ITransitionFragmentCallback
import com.example.tracnghiem.databinding.FragmentCourseLessonBinding
import com.example.tracnghiem.utils.showView
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_courses.*
import kotlinx.android.synthetic.main.layout_empty.*
import kotlinx.android.synthetic.main.layout_shimmer_flexible.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class CourseLessonFragment(private val transitionFragmentCallback: ITransitionFragmentCallback) :
    BaseFragment<FragmentCourseLessonBinding>() {
    override fun setLayoutId(): Int = R.layout.fragment_course_lesson

    private val mViewModel: CourseLessonViewModel by viewModel()

    override fun initViewModel() {
        (binding as FragmentCourseLessonBinding).viewModel = mViewModel
        shimmerLayout.startShimmer()
    }

    override fun initData(data: Bundle?) {
        data?.let {
            val mMosId = data.getString("KeyId")
            mViewModel.setMosId(mMosId)
        }
        mViewModel.requestListLesson()
    }

    override fun iniListener() {
        mViewModel.getListLessonLiveData().observe(this, Observer { result ->
            swipeRefreshLayout?.isRefreshing = false
            shimmerLayout.visibility = View.GONE
            shimmerLayout.stopShimmer()
            if (result == null) {
                // show empty
                tvEmpty.showView()
                mViewModel.updateData(arrayListOf())
                return@Observer
            }
            result.let { mViewModel.updateData(result) }
        })

        swipeRefreshLayout.setOnRefreshListener {
            mViewModel.requestListLesson()
        }

        mViewModel.getListChapterSelectedLiveData().observe(this, Observer { data ->
            // so some thing ?
            // start activity practice

        })

    }


}