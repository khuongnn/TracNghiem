package com.example.tracnghiem.utils.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tracnghiem.R

object BindingAdapterHelper {
    @JvmStatic
    @BindingAdapter("android:src")
    fun ImageView.setImageUrl(url: String?) {
        if (url.isNullOrEmpty()) {
            setImageResource(R.mipmap.img_no_image)
            return
        }
        val requestOption = RequestOptions()
            .placeholder(R.drawable.img_placeholder)
            .error(R.mipmap.img_no_image).override(width, height)
        Glide.with(context).load(url).apply(requestOption).into(this)
    }
    @JvmStatic
    @BindingAdapter("bind:textPosition")
    fun TextView.setTextWithPosition(position: Int) {
        text = position.toString()
    }

    @JvmStatic
    @BindingAdapter("onInterruptClick")
    fun View.setOnClickListener(runnable: Runnable) {
        var mLastClickTime = 0L
        setOnClickListener {
            val currentTime = System.currentTimeMillis()
            if (currentTime - mLastClickTime > 1000) {
                runnable.run()
                mLastClickTime = currentTime
            }
        }
    }

    // set time count down
    @JvmStatic
    @BindingAdapter("spotMovieSize")
    fun TextView.setMovieSize(size: Int) {
        if (size == 0) visibility = View.GONE
        else {
            visibility = View.VISIBLE
            text = ""
        }
    }
}