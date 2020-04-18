package com.example.tracnghiem.network.model.request

import com.google.gson.annotations.SerializedName

data class LoginRequestBody (
    @SerializedName("username")
    val email : String,
    @SerializedName("password")
    val password : String
)