package com.example.tracnghiem.data.model

data class Questions(
    var id: String? = "",
    var a: String? = "",
    var b: String?= "",
    var c: String?="",
    var d: String?="",
    var correct_answer: String?="",
    var position: Int = 0,
    var question: String?= "",
    var choseId: Int?= -1,
    var setChoiceAns: String?= "",
    var  idQuestion: String?=""
)