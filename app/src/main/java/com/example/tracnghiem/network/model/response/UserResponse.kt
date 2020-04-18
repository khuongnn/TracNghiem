package com.example.tracnghiem.network.model.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("token")
    var token : String? = "",
    @SerializedName("user")
    var user : User?
)