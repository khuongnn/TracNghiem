package com.example.tracnghiem.utils.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NoneSwipeViewPager :ViewPager {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private var isSwipeEnable = true

    fun setSwipeEnable(isEnable : Boolean) {
        isSwipeEnable = isEnable
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return if (isSwipeEnable)
            super.onInterceptTouchEvent(ev)
        else false
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return if (isSwipeEnable)
            super.onTouchEvent(ev)
        else false
    }
}