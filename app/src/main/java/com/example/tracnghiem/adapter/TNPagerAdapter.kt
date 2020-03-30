package com.example.tracnghiem.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TNPagerAdapter(fragment: FragmentManager) : FragmentPagerAdapter(
    fragment,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    private val mFragment = ArrayList<Pair<Fragment, String>>()

    override fun getItem(position: Int): Fragment {
        return mFragment[position].first
    }

    override fun getCount(): Int {
        return mFragment.size
    }

    fun addFragment(fragment: Fragment, title: String = "") = mFragment.add(Pair(fragment, title))

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragment[position].second
    }
}