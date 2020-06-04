package com.example.tracnghiem.activity.main.mainpractice.practicenavigate

import com.example.tracnghiem.R
import com.example.tracnghiem.base.BaseActivity
import com.example.tracnghiem.databinding.ActivityPracticeNavigationBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PracticeNavigateActivity : BaseActivity<ActivityPracticeNavigationBinding>() {
    override fun setLayoutId(): Int = R.layout.activity_practice_navigation
    private val mViewModel: PracticeNavigateViewModel by viewModel()
    override fun initView() {
        (binding as ActivityPracticeNavigationBinding).viewModel = mViewModel
    }

    override fun initViewModel() {

    }

    override fun initData() {

    }

    override fun initListener() {

    }
}