package com.example.tracnghiem.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.tracnghiem.R
import io.opencensus.trace.MessageEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseActivity<T : ViewDataBinding?> : AppCompatActivity() {
    lateinit var binding: ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, setLayoutId())
        // fun do you want to do
        initData()
        initListener()

        initViewModel()
        initView()
    }

    abstract fun setLayoutId(): Int
    abstract fun initView()
    abstract fun initViewModel()
    abstract fun initData()
    abstract fun initListener()

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    // init handler fragment
    fun addFragment(fragment: Fragment, layoutId: Int) {
        supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slider_in_right,
            R.anim.slider_out_left,
            R.anim.slider_in_left,
            R.anim.slider_out_right
        ).add(layoutId, fragment).addToBackStack(null).commitAllowingStateLoss()
    }

    fun showFragment(fragment: Fragment, isFromLeft: Boolean = false) {
        if (isFromLeft) {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slider_in_left, R.anim.slider_out_right)
                .show(fragment).commitAllowingStateLoss()
        } else {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slider_in_right, R.anim.slider_out_left)
                .show(fragment).commitAllowingStateLoss()
        }
    }

    fun hideFragment(fragment: Fragment, isFromLeft: Boolean = false) {
        if (isFromLeft) {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slider_in_left, R.anim.slider_out_right)
                .hide(fragment).commitAllowingStateLoss()
        } else {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slider_in_right, R.anim.slider_out_left)
                .hide(fragment).commitAllowingStateLoss()
        }
    }

    fun popFragment() {
        supportFragmentManager.popBackStack()
    }
}