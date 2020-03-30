package com.example.tracnghiem.utils.view

import android.animation.LayoutTransition
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.example.tracnghiem.R
import kotlinx.android.synthetic.main.layout_bottom_navigation.view.*

class TNBottomNavigation : LinearLayout, View.OnClickListener {
    companion object {
        const val TAB_COURSE_INDEX = 0
        const val TAB_PRACTICE_INDEX = 1
        const val TAB_MY_PAGE_INDEX = 2
    }

    private var mCurrentPosition = TAB_COURSE_INDEX

    // Activity call
    fun getCurrentPosition() = mCurrentPosition
    fun setCurrentPosition(position: Int){
        switchTabTo((position))
    }

    private var mSelectedPosition = TAB_COURSE_INDEX

    private var mTabChangeListener: OnTabEventListener? = null

    constructor(context: Context?, attributeSet: AttributeSet?) : super(context, attributeSet) {
        // init context
        init(context)
    }

    constructor(context: Context?) : super(context) {
        // init context
        init(context)
    }

    private fun init(context: Context?) {
        LayoutInflater.from(context).inflate(R.layout.layout_bottom_navigation, this, true)

        llCourses.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        llPractice.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        llMyPage.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        llCourses.setOnClickListener(this)
        llMyPage.setOnClickListener(this)
        llPractice.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            llCourses -> {
                // do some thing
                switchTabTo(TAB_COURSE_INDEX)
            }
            llPractice -> {
                switchTabTo(TAB_PRACTICE_INDEX)
            }
            llMyPage -> {
                switchTabTo(TAB_MY_PAGE_INDEX)
            }
        }
    }

    private fun switchTabTo(position: Int) {
        mSelectedPosition = position
        if (mCurrentPosition != mSelectedPosition) {
            // set color tab
            activeTab(mSelectedPosition)
            unActiveTab(mCurrentPosition)
            mTabChangeListener?.onTabChange(mCurrentPosition, mSelectedPosition)
            mTabChangeListener?.onTabSelected(position, true)
            mCurrentPosition = mSelectedPosition
        } else {
            // listener
            mTabChangeListener?.onTabSelected(position, false)
        }
    }

    // Activity call
    fun addOnTabChangeListener(listener: OnTabEventListener) {
        mTabChangeListener = listener
    }

    fun activeTab(position: Int) {
        when (position) {
            TAB_COURSE_INDEX -> {
                imgCourses.setImageResource(R.drawable.ic_course)
                tvCourse.visibility = View.VISIBLE
            }
            TAB_PRACTICE_INDEX -> {
                imgPractice.setImageResource(R.drawable.book)
                tvPractice.visibility = View.VISIBLE
            }
            TAB_MY_PAGE_INDEX -> {
                imgMyPage.setImageResource(R.drawable.setting)
                tvMyPage.visibility = View.VISIBLE
            }
        }
    }

    fun unActiveTab(position: Int) {
        when (position) {
            TAB_COURSE_INDEX -> {
                imgCourses.setImageResource(R.drawable.ic_course_inactive)
                tvCourse.visibility = View.GONE
            }
            TAB_PRACTICE_INDEX -> {
                imgPractice.setImageResource(R.drawable.book_inactive)
                tvPractice.visibility = View.GONE
            }
            TAB_MY_PAGE_INDEX -> {
                imgMyPage.setImageResource(R.drawable.setting_inactive)
                tvMyPage.visibility = View.GONE
            }
        }
    }

    interface OnTabEventListener {
        fun onTabChange(oldPosition: Int, currentPosition: Int)
        fun onTabSelected(position: Int, isChange: Boolean)
    }
}