package com.example.tracnghiem.activity.main

import com.example.tracnghiem.base.BaseViewModel
import timber.log.Timber

class HomeViewModel() : BaseViewModel() {
    companion object {
        const val TAG = "MainViewModel"
    }

    override fun onError(error: Throwable) {
        Timber.tag(TAG).e("Error: $error")
    }

}