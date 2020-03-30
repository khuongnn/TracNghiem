package com.example.tracnghiem.data.model

import com.google.firebase.firestore.DocumentReference

data class Mos(
    var id: String? = "",
    var imageUrl: String? = "",
    var nameMos: String? = "",
    var position: Int = 0,
    var detail: String? = "",
    var creator: String? = "",
    var content: ArrayList<DocumentReference>? = arrayListOf(),
    var lesson: ArrayList<DocumentReference>? = arrayListOf()

) {

}