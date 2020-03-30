package com.example.tracnghiem.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class SliderPageAdapter(fragment: FragmentManager) : FragmentPagerAdapter(fragment,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

  private var mFragment = ArrayList<Triple< Fragment,String,Int>> ()


     override fun getItem(position: Int): Fragment {
      return mFragment[position].first
     }

     override fun getCount(): Int {
      return mFragment.size
     }

    fun addFragment(fragment: Fragment, title: String = "",position: Int) {
        mFragment.add(Triple(fragment, title,position))
    }

}