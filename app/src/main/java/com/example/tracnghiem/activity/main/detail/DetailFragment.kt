package com.example.tracnghiem.activity.main.detail

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.example.tracnghiem.R
import com.example.tracnghiem.base.BaseFragment
import com.example.tracnghiem.base.ITransitionFragmentCallback
import com.example.tracnghiem.databinding.FragmentDetailBinding
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment(private var transitionFragmentCallback: ITransitionFragmentCallback) :
    BaseFragment<FragmentDetailBinding>() {
    override fun setLayoutId(): Int = R.layout.fragment_detail

    override fun initViewModel() {

    }

    override fun initData(data: Bundle?) {

        data?.let {
            val mData = data.getString("KeyChapterData")
            val mTitle = data.getString("KeyChapterTitle")
            tvTitle.text = mTitle
            webText.webViewClient = WebViewClient()
            webText.webChromeClient = WebChromeClient()
            webText.settings.builtInZoomControls = true
            webText.settings.domStorageEnabled = true
            webText.loadDataWithBaseURL(null, mData, "text/html; charset=utf-8", "UTF-8", null)
        }
    }

    override fun iniListener() {
        imgBack.setOnClickListener {
            transitionFragmentCallback.onBackPressed()
        }
    }

}