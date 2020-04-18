package com.example.tracnghiem.network.model.response

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("userId")
    var userId: String? = "",
    @SerializedName("username")
    var username: String? = "",
    @SerializedName("fullName")
    var fullName: String? = "",
    @SerializedName("avatarUrl")
    var avatarUrl: String? = ""
)