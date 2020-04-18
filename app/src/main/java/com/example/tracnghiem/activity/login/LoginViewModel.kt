package com.example.tracnghiem.activity.login

import androidx.lifecycle.MutableLiveData
import com.example.tracnghiem.base.BaseViewModel
import com.example.tracnghiem.network.TNApiService
import com.example.tracnghiem.network.model.request.LoginRequestBody

class LoginViewModel(private val tnApiService: TNApiService) : BaseViewModel() {
    private val mOnLoginSuccess = MutableLiveData<Boolean>()

    fun onLoginSuccess() = mOnLoginSuccess


    override fun onError(error: Throwable) {
        mOnLoginSuccess.value = false
    }

    val loginResponseLiveData = tnApiService.loginResponseLiveData
    fun login(email: String, password: String) {
        val user = LoginRequestBody(email, password)
        tnApiService.login(user)
    }

}