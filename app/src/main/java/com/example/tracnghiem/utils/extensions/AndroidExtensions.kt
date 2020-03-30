package com.example.tracnghiem.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tracnghiem.R
import java.util.ArrayList


fun ImageView.setImageUrl(url: String?) {
    if (url.isNullOrEmpty()) {
        setImageResource(R.drawable.img_no_image)
        return
    }
    val requestOption = RequestOptions()
        .placeholder(R.drawable.img_placeholder)
        .error(R.drawable.img_no_image).override(width, height)
    Glide.with(context).load(url).apply(requestOption).into(this)
}

fun ArrayList<String>?.tagsToString(): String {
    if (this.isNullOrEmpty()) return ""
    return this.joinToString(" ") {
        val tag = it.trim()
        if (tag[0] == '#') tag
        else "#$tag"
    }
}