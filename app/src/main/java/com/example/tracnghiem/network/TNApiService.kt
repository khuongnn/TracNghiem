package com.example.tracnghiem.network

import androidx.lifecycle.MutableLiveData
import com.example.tracnghiem.network.model.base.BaseLiveData
import com.example.tracnghiem.network.model.base.BaseResponse
import com.example.tracnghiem.network.model.request.LoginRequestBody
import com.example.tracnghiem.network.model.response.InitResponse
import com.example.tracnghiem.network.model.response.UserResponse
import com.example.tracnghiem.network.services.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class TNApiService(private val apiService: ApiService) {
    //example
    val initResponseLiveData = MutableLiveData<BaseLiveData<Response<BaseResponse<InitResponse>>>>()
    fun init() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val dataReceived = apiService.init()
                initResponseLiveData.postValue(BaseLiveData(dataReceived))
            } catch (e: Exception) {
                initResponseLiveData.postValue(BaseLiveData(null))
            }
        }
    }




    val loginResponseLiveData = MutableLiveData<BaseLiveData<Response<UserResponse>>>()
    fun login(body: LoginRequestBody) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val dataReceived = apiService.login(body)
                loginResponseLiveData.postValue(BaseLiveData(dataReceived))

            } catch (e: Exception) {
                loginResponseLiveData.postValue(BaseLiveData(null))
            }
        }
    }


}