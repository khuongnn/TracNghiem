package com.example.tracnghiem.network.services

import com.example.tracnghiem.network.model.base.BaseResponse
import com.example.tracnghiem.network.model.request.LoginRequestBody
import com.example.tracnghiem.network.model.response.InitResponse
import com.example.tracnghiem.network.model.response.UserResponse
import com.example.tracnghiem.utils.config.AppConfig
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // not use
    @GET("init")
    suspend fun init(): Response<BaseResponse<InitResponse>>

    // Login
    @POST("/api/user/login")
    @Headers(value = [AppConfig.HEADER])
    suspend fun login(@Body body: LoginRequestBody): Response<UserResponse>
}