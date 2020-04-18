package com.example.tracnghiem.network.model.response

import com.google.gson.annotations.SerializedName

data class InitResponse(
    var expired : Long?,
//
    @SerializedName("expired_token")
    var expiredToken : Long?

)