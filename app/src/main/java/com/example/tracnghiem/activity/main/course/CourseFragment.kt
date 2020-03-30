package com.example.tracnghiem.activity.main.course

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.tracnghiem.R
import com.example.tracnghiem.activity.main.course.coursedetail.CourseDetailFragment
import com.example.tracnghiem.base.BaseFragment
import com.example.tracnghiem.base.setData
import com.example.tracnghiem.databinding.FragmentCoursesBinding
import com.example.tracnghiem.utils.showView
import kotlinx.android.synthetic.main.fragment_courses.*
import kotlinx.android.synthetic.main.layout_empty.*
import kotlinx.android.synthetic.main.layout_shimmer_flexible.*
import org.koin.android.viewmodel.ext.android.viewModel

class CourseFragment : BaseFragment<FragmentCoursesBinding>(), ICourseCallback {
    private val mViewModel: CourseViewModel by viewModel()
    override fun setLayoutId(): Int = R.layout.fragment_courses

    override fun initViewModel() {
        (binding as FragmentCoursesBinding).viewModel = mViewModel
        shimmerLayout.startShimmer()
    }

    override fun initData(data: Bundle?) {
        mViewModel.requestListMosDetail()
    }

    override fun iniListener() {
        mViewModel.getListMosLiveData().observe(this, Observer { result ->
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
            mViewModel.requestListMosDetail()
        }
        mViewModel.getMosSelectedLiveData().observe(this, Observer { data ->
            addFragment(
                CourseDetailFragment(this).setData(
                    bundleOf(
                        Pair("KeyId", data.first),
                        Pair("KeyName", data.second)
                    )
                ), R.id.frameFragment
            )
        })

    }

    override fun addFragment(fragment: Fragment) {
        addFragment(fragment, R.id.frameFragment)
    }


    override fun onBackPressed() {
        activity?.onBackPressed()
    }
}