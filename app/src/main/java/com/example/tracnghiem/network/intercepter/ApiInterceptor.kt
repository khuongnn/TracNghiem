package com.example.tracnghiem.network.intercepter

import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            //Add header  or something you want
            .build()
        return chain.proceed(request)
    }
}