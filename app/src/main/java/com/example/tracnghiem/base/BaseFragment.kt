package com.example.tracnghiem.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.tracnghiem.R

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    lateinit var binding: ViewDataBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<T>(inflater, setLayoutId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // init
        initViewModel()
        iniListener()
        initData(arguments)
    }

    abstract fun initViewModel()
    abstract fun initData(data: Bundle?)
    abstract fun iniListener()

    abstract fun setLayoutId(): Int

    // fun navigate fragment
    fun addFragment(fragment: Fragment, layoutId: Int) {
        childFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slider_in_right,
            R.anim.slider_out_left,
            R.anim.slider_in_left,
            R.anim.slider_out_right
        ).add(layoutId, fragment).addToBackStack(null).commitAllowingStateLoss()
    }

    fun replaceFragment(fragment: Fragment, layoutId: Int) {
        childFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slider_in_right,
            R.anim.slider_out_left,
            R.anim.slider_in_left,
            R.anim.slider_out_right
        ).replace(layoutId, fragment).addToBackStack(null).commitAllowingStateLoss()
    }

    // back press
    fun popFragment(): Boolean {
        if (childFragmentManager.backStackEntryCount > 0) {
            childFragmentManager.popBackStack()
            return false
        }
        return true
    }
    fun popAllBackStackFragment(){
        childFragmentManager.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

}

// fun to navigate data
fun Fragment.setData(bundle: Bundle): Fragment {
    this.arguments = bundle
    return this
}