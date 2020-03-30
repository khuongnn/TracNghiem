package com.example.tracnghiem.base

import androidx.fragment.app.Fragment

interface ITransitionFragmentCallback {
    fun addFragment(fragment: Fragment)

    fun onBackPressed()
}