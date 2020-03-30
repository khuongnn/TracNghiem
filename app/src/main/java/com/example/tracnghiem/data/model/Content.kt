package com.example.tracnghiem.data.model

data class Content(
    var id: String? = "",
    var data: String? = "",
    var creator : String? = "",
    var detail: String?= "",
    var title: String? = "",
    val imageUrl: String? = "",
    val position: Int = 0
) {

}