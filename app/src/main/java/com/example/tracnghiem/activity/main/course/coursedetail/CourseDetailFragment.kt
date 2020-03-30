package com.example.tracnghiem.activity.main.course.coursedetail

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.viewpager.widget.ViewPager
import com.example.tracnghiem.R
import com.example.tracnghiem.activity.main.course.chapter.CourseChapterFragment
import com.example.tracnghiem.activity.main.course.lesson.CourseLessonFragment
import com.example.tracnghiem.adapter.TNPagerAdapter
import com.example.tracnghiem.base.BaseFragment
import com.example.tracnghiem.base.ITransitionFragmentCallback
import com.example.tracnghiem.base.setData
import com.example.tracnghiem.databinding.FragmentCoursesBinding
import kotlinx.android.synthetic.main.fragment_course_detail.*

class CourseDetailFragment(private val transitionFragmentCallback: ITransitionFragmentCallback) :
    BaseFragment<FragmentCoursesBinding>() {
    override fun setLayoutId(): Int = R.layout.fragment_course_detail
    private lateinit var mPagerAdapter: TNPagerAdapter

    override fun initViewModel() {
        mPagerAdapter = TNPagerAdapter(childFragmentManager)
    }

    override fun initData(data: Bundle?) {
        vpMosDetail.adapter = mPagerAdapter
        data?.let {
            val mosId = data.getString("KeyId")
            val mosName = data.getString("KeyName")

            tvMosName.text = mosName
            val listMosChapterFragment = CourseChapterFragment(transitionFragmentCallback)
            listMosChapterFragment.setData(bundleOf(Pair("KeyId",mosId)))

            val listLessonFragment  =  CourseLessonFragment(transitionFragmentCallback)
            listLessonFragment.setData(bundleOf(Pair("KeyId",mosId)))

            mPagerAdapter.addFragment(listMosChapterFragment,"CHAPTER")
            mPagerAdapter.addFragment(listLessonFragment, "LESSON")

            mPagerAdapter.notifyDataSetChanged()

        }


    }

    override fun iniListener() {
        imgBack.setOnClickListener{
            transitionFragmentCallback.onBackPressed()
        }

        vpMosDetail.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
                //Do nothing
            }
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                //Do nothing
            }

            override fun onPageSelected(position: Int) {
               // listen and do something
            }
        })

    }


}