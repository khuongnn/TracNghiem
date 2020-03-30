package com.example.tracnghiem.activity.main.practice

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.tracnghiem.R
import com.example.tracnghiem.activity.main.mainpractice.PracticeActivity
import com.example.tracnghiem.base.BaseFragment
import com.example.tracnghiem.databinding.FragmentPracticeBinding
import kotlinx.android.synthetic.main.fragment_practice.*
import kotlinx.android.synthetic.main.layout_shimmer_flexible.*
import org.koin.android.viewmodel.ext.android.viewModel

class PracticeFragment : BaseFragment<FragmentPracticeBinding>() {
    override fun setLayoutId(): Int = R.layout.fragment_practice
    private val mViewModel: PracticeViewModel by viewModel()
    override fun initViewModel() {
        (binding as FragmentPracticeBinding).viewModel = mViewModel
        // show shimmerLayout
        shimmerLayout.startShimmer()
    }

    override fun initData(data: Bundle?) {
        mViewModel.requestListQuizDetail()
    }

    override fun iniListener() {
        mViewModel.getListQuizLiveData().observe(this, Observer { result ->
            swipeRefreshLayout?.isRefreshing = false
            shimmerLayout.visibility = View.GONE
            shimmerLayout.stopShimmer()
            if (result == null) {
                //do some thing
            }
            result?.let { mViewModel.updateData(result) }
        })

        swipeRefreshLayout.setOnRefreshListener {
            mViewModel.requestListQuizDetail()
        }
        mViewModel.getQuizSelectedLiveData().observe(this, Observer { pair ->
            openPracticeActivity(pair)
        })


    }

    private fun openPracticeActivity(pair: Pair<String?, String?>?) {
        activity?.let {
            val intent = Intent(it, PracticeActivity::class.java)
            intent.putExtra("quizId", pair?.first)
            intent.putExtra("quizName", pair?.second)
            it.startActivity(intent)
        }
    }
}