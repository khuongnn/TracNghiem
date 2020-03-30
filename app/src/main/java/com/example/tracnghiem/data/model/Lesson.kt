package com.example.tracnghiem.data.model

import com.google.firebase.firestore.DocumentReference

data class Lesson(
    var id: String? = "",
    var nameLesson: String? = "",
    var imageUrl: String? = "",
    var creator: String? = "",
    var detail: String? = "",
    var position: Int = 0,
    var questions: ArrayList<DocumentReference>? = arrayListOf()
) {
}