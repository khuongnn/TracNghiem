package com.example.tracnghiem.activity.main


import android.content.Intent
import android.content.pm.ActivityInfo
import com.example.tracnghiem.R
import com.example.tracnghiem.activity.main.course.CourseFragment
import com.example.tracnghiem.activity.main.mypage.MyPageFragment
import com.example.tracnghiem.activity.main.practice.PracticeFragment
import com.example.tracnghiem.base.BaseActivity
import com.example.tracnghiem.databinding.ActivityHomeBinding
import com.example.tracnghiem.event.ShowNetworkErrorDialogEvent
import com.example.tracnghiem.utils.dialog.DialogNetworkError
import com.example.tracnghiem.utils.view.TNBottomNavigation
import kotlinx.android.synthetic.main.activity_home.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override fun setLayoutId(): Int = R.layout.activity_home
    private val mCourseFragment: CourseFragment by lazy {
        CourseFragment()
    }
    private val mMyPageFragment: MyPageFragment by lazy {
        MyPageFragment()
    }
    private val mPracticeFragment: PracticeFragment by lazy {
        PracticeFragment()
    }

    override fun initView() {
        // android not recommend
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        addFragment(mCourseFragment, R.id.mainContainer)
        addFragment(mMyPageFragment, R.id.mainContainer)
        addFragment(mPracticeFragment, R.id.mainContainer)


        showFragmentAt(TNBottomNavigation.TAB_COURSE_INDEX)
    }

    override fun initViewModel() {
    }

    override fun initData() {
    }

    override fun initListener() {
        tnBottomNavigation.addOnTabChangeListener(object : TNBottomNavigation.OnTabEventListener {
            override fun onTabSelected(position: Int, isChange: Boolean) {
                // add  to back stack
                if (isChange) return
                when (position) {
                    TNBottomNavigation.TAB_COURSE_INDEX -> {
                        mCourseFragment.popAllBackStackFragment()
                    }
                    TNBottomNavigation.TAB_PRACTICE_INDEX -> {
                        mPracticeFragment.popAllBackStackFragment()
                    }
                    TNBottomNavigation.TAB_MY_PAGE_INDEX -> {
                        mMyPageFragment.popAllBackStackFragment()
                    }


                }

            }

            override fun onTabChange(oldPosition: Int, currentPosition: Int) {
                // show fragment
                showFragmentAt(currentPosition, oldPosition > currentPosition)
            }
        })
    }

    private fun showFragmentAt(position: Int, isFromLeft: Boolean = false) {
        when (position) {
            TNBottomNavigation.TAB_COURSE_INDEX -> {
                showFragment(mCourseFragment, isFromLeft)
                hideFragment(mMyPageFragment, isFromLeft)
                hideFragment(mPracticeFragment, isFromLeft)

            }
            TNBottomNavigation.TAB_PRACTICE_INDEX -> {
                showFragment(mPracticeFragment, isFromLeft)
                hideFragment(mCourseFragment, isFromLeft)
                hideFragment(mMyPageFragment, isFromLeft)

            }
            TNBottomNavigation.TAB_MY_PAGE_INDEX -> {
                showFragment(mMyPageFragment, isFromLeft)
                hideFragment(mCourseFragment, isFromLeft)
                hideFragment(mPracticeFragment, isFromLeft)

            }

        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onBackPressed() {
        val currentPosition = tnBottomNavigation.getCurrentPosition()
        var isFinishActivity = false
        when (currentPosition) {
            TNBottomNavigation.TAB_COURSE_INDEX->{
                isFinishActivity = mCourseFragment.popFragment()
            }
            TNBottomNavigation.TAB_PRACTICE_INDEX->{
                isFinishActivity = mPracticeFragment.popFragment()
            }
            TNBottomNavigation.TAB_MY_PAGE_INDEX->{
                isFinishActivity = mMyPageFragment.popFragment()
            }

        }

        // Activity die
        if (isFinishActivity){
            if (currentPosition != TNBottomNavigation.TAB_COURSE_INDEX){
                tnBottomNavigation.setCurrentPosition(TNBottomNavigation.TAB_COURSE_INDEX)
            }else{
                finish()
            }
        }
    }

    private var mDialogNetworkError : DialogNetworkError? = null
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ShowNetworkErrorDialogEvent) {
        if (mDialogNetworkError?.isShowing() != true){
            mDialogNetworkError = DialogNetworkError(this)
            mDialogNetworkError?.show()
        }
    }
}
