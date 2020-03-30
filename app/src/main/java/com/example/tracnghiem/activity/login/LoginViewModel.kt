package com.example.tracnghiem.activity.login

import androidx.lifecycle.MutableLiveData
import com.example.tracnghiem.base.BaseViewModel

class LoginViewModel : BaseViewModel() {
    private val mOnLoginSuccess = MutableLiveData<Boolean>()

    fun onLoginSuccess() = mOnLoginSuccess

    fun login(email: String, password: String) {
        mOnLoginSuccess.value = true
    }

    override fun onError(error: Throwable) {
        mOnLoginSuccess.value = false
    }

}