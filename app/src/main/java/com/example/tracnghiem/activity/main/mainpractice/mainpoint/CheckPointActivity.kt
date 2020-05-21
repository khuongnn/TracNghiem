package com.example.tracnghiem.activity.main.mainpractice.mainpoint

import com.example.tracnghiem.R
import com.example.tracnghiem.base.BaseActivity
import com.example.tracnghiem.databinding.ActivityCheckPointBinding
import org.koin.android.viewmodel.ext.android.viewModel

class CheckPointActivity() : BaseActivity<ActivityCheckPointBinding>() {
    override fun setLayoutId(): Int = R.layout.activity_check_point

    private val mViewModel: CheckPointViewModel by viewModel()
    override fun initView() {
        (binding as ActivityCheckPointBinding).viewModel = mViewModel


    }

    override fun initViewModel() {

    }

    override fun initData() {

    }

    override fun initListener() {

    }

}