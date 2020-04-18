package com.example.tracnghiem.activity.main.mypage

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.example.tracnghiem.R
import com.example.tracnghiem.activity.main.sharewith.ShareWithActivity
import com.example.tracnghiem.base.BaseFragment
import com.example.tracnghiem.databinding.FragmentMyPageBinding
import com.example.tracnghiem.utils.view.showCustomToast
import com.facebook.FacebookSdk.getApplicationContext
import kotlinx.android.synthetic.main.fragment_my_page.*
import kotlinx.android.synthetic.main.layout_custom_toast.*


class MyPageFragment : BaseFragment<FragmentMyPageBinding>() {
    private val toast: Toast = Toast(getApplicationContext())

    override fun setLayoutId(): Int = R.layout.fragment_my_page
    override fun initViewModel() {

    }

    override fun initData(data: Bundle?) {

    }

    override fun iniListener() {
        llHistory.setOnClickListener {
            toast.showCustomToast(getApplicationContext(), "Hiện chưa có lịch sử học tập nào!")
        }

        llBookmark.setOnClickListener {
            toast.showCustomToast(getApplicationContext(), "Chức năng đang trong thời gian phát triển")
        }

        llChart.setOnClickListener {
            toast.showCustomToast(getApplicationContext(), "Chức năng đang trong thời gian phát triển")
        }

        llShare.setOnClickListener {
            val intent = Intent(getApplicationContext(), ShareWithActivity::class.java)
            startActivity(intent)
        }

        llEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, email)
            intent.putExtra(Intent.EXTRA_SUBJECT, "Đóng góp ý kiến !")
            try {
                startActivity(Intent.createChooser(intent, "Send email... "))
            } catch (ex: Exception) {
                toast.showCustomToast(getApplicationContext(), "Error")
            }
        }
        llRate.setOnClickListener {
            toast.showCustomToast(getApplicationContext(), "Hello From Android Tech Point")
        }
    }

    companion object {
        var email = arrayOf<String>("nguyenduyhieuk94@gmail.com")
    }

}
