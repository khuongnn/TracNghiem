package com.example.tracnghiem.activity.login.signup

import com.example.tracnghiem.R
import com.example.tracnghiem.activity.login.LoginViewModel
import com.example.tracnghiem.base.BaseActivity
import com.example.tracnghiem.databinding.ActivitySignUpBinding
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber


class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {
    override fun setLayoutId(): Int = R.layout.activity_sign_up
    private val mViewModel: LoginViewModel by viewModel()

    override fun initView() {
        Timber.tag(TAG).e("Init view")
    }

    override fun initViewModel() {
        Timber.tag(TAG).e("Init view model")


    }

    override fun initData() {
    }

    override fun initListener() {


        }





    companion object {
        private const val TAG = "LoginActivity"
    }

}
