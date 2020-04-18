package com.example.tracnghiem.activity.main.course.chapter

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.example.tracnghiem.R
import com.example.tracnghiem.activity.main.detail.DetailFragment
import com.example.tracnghiem.base.BaseFragment
import com.example.tracnghiem.base.ITransitionFragmentCallback
import com.example.tracnghiem.base.setData
import com.example.tracnghiem.databinding.FragmentCourseChapterBinding
import com.example.tracnghiem.utils.hideView
import com.example.tracnghiem.utils.showView
import kotlinx.android.synthetic.main.fragment_courses.*
import kotlinx.android.synthetic.main.layout_empty.*
import kotlinx.android.synthetic.main.layout_shimmer_flexible.*
import org.koin.android.viewmodel.ext.android.viewModel

class CourseChapterFragment(private val transitionFragmentCallback: ITransitionFragmentCallback) :
    BaseFragment<FragmentCourseChapterBinding>() {
    override fun setLayoutId(): Int = R.layout.fragment_course_chapter
    private val mViewModel: CourseChapterViewModel by viewModel()

    override fun initViewModel() {
        (binding as FragmentCourseChapterBinding).viewModel = mViewModel
        shimmerLayout.startShimmer()
    }

    override fun initData(data: Bundle?) {
        data?.let {
            val mMosId = data.getString("KeyId")
            mViewModel.setMosId(mMosId)
        }
        mViewModel.requestListChapter()
    }

    override fun iniListener() {
        mViewModel.getListChapterLiveData().observe(this, Observer { result ->
            swipeRefreshLayout?.isRefreshing = false
            shimmerLayout.visibility = View.GONE
            shimmerLayout.stopShimmer()
            tvEmpty.hideView()
            if (result == null) {
                // show empty
                tvEmpty.showView()
                mViewModel.updateData(arrayListOf())
                return@Observer
            }
            result.let { mViewModel.updateData(result) }
        })
        swipeRefreshLayout.setOnRefreshListener {
            mViewModel.requestListChapter()
        }
        mViewModel.getListChapterSelectedLiveData().observe(this, Observer { data ->
            val fragment = DetailFragment(transitionFragmentCallback)
            fragment.setData(
                bundleOf(
                    Pair("KeyChapterData", data.first),
                    Pair("KeyChapterTitle", data.second)
                )
            )
            transitionFragmentCallback.addFragment(fragment)

        })

    }


}