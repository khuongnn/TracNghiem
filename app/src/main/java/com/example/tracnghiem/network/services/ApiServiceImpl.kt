package com.example.tracnghiem.network.services

import com.example.tracnghiem.network.model.base.BaseResponse
import com.example.tracnghiem.network.model.request.LoginRequestBody
import com.example.tracnghiem.network.model.response.InitResponse
import com.example.tracnghiem.network.model.response.UserResponse
import retrofit2.Response

open class ApiServiceImpl(private val service: ApiService) : ApiService {

    override suspend fun init(): Response<BaseResponse<InitResponse>> {
        return service.init()
    }

    override suspend fun login(body: LoginRequestBody): Response<UserResponse> {
        return service.login(body)
    }


}