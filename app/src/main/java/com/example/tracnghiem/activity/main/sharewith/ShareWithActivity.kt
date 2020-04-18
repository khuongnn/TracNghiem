package com.example.tracnghiem.activity.main.sharewith

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import com.example.tracnghiem.R
import com.example.tracnghiem.base.BaseActivity
import com.example.tracnghiem.databinding.ActivityShareWithBinding
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_share_with.*

class ShareWithActivity : BaseActivity<ActivityShareWithBinding>() {
    override fun setLayoutId(): Int = R.layout.activity_share_with
    override fun initView() {

    }

    override fun initViewModel() {

    }

    override fun initData() {
    }

    override fun initListener() {
        btnShareFb.setOnClickListener {
            shareAppWithSocial(applicationContext, FACEBOOK_PACKAGE_NAME, "share", "Ứng dụng học tập, ôn thi tin học văn phòng 2020. Tải app tại: ")
        }
        btnShareLine.setOnClickListener {
            shareAppWithSocial(applicationContext, LINE_PACKAGE_NAME, "share", "Ứng dụng học tập, ôn thi tin học văn phòng 2020. Tải app tại:")
        }
        btnShareTw.setOnClickListener {
            shareAppWithSocial(applicationContext, TWITTER_PACKAGE_NAME, "share", "Ứng dụng học tập, ôn thi tin học văn phòng 2020. Tải app tại:")
        }
    }

    private fun shareAppWithSocial(
        context: Context,
        application: String?,
        title: String?,
        description: String?) {
        // val  isAppInstalled : Boolean = isAppAvailable(context, application)

        val intent = Intent()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.action = Intent.ACTION_SEND
        intent.setPackage(application)
        intent.putExtra(Intent.EXTRA_TITLE, title)
        intent.putExtra(Intent.EXTRA_TEXT, description)
        intent.type = "text/plain"

        try {
            // Start the specific social application
            context.startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            // The application does not exist
            context.startActivity(intent)
            Toast.makeText(context, "app have not been installed.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun isAppAvailable(context: Context, packageName: String?): Boolean {
        return try {
            context.packageManager.getApplicationInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }


    companion object {
        const val FACEBOOK_PACKAGE_NAME = "com.facebook.katana"
        const val TWITTER_PACKAGE_NAME = "com.twitter.android"
        const val LINE_PACKAGE_NAME = "jp.naver.line.android"
    }
}