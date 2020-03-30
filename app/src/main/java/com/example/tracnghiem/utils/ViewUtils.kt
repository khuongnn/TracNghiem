package com.example.tracnghiem.utils

import android.view.View

fun View.showView() {
    visibility = View.VISIBLE
}

fun View.invisibleView() {
    visibility = View.INVISIBLE
}

fun View.hideView() {
    visibility = View.GONE
}

fun View.isVisible(isVisible: Boolean) {
    if (isVisible) showView()
    else hideView()
}