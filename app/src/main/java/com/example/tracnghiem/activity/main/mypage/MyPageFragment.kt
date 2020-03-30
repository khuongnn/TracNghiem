package com.example.tracnghiem.activity.main.mypage

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.example.tracnghiem.R
import com.example.tracnghiem.base.BaseFragment
import com.example.tracnghiem.databinding.FragmentMyPageBinding
import kotlinx.android.synthetic.main.fragment_my_page.*
import kotlinx.android.synthetic.main.layout_custom_toast.*
import java.lang.reflect.Array


class MyPageFragment : BaseFragment<FragmentMyPageBinding>() {


    override fun setLayoutId(): Int = R.layout.fragment_my_page
    override fun initViewModel() {

    }

    override fun initData(data: Bundle?) {

    }

    override fun iniListener() {
        llHistory.setOnClickListener {
            showCustomToast("Empty")
        }
        llBookmark.setOnClickListener {
            showCustomToast("Empty")
        }
        llChart.setOnClickListener {
            showCustomToast("Empty")
        }
        llShare.setOnClickListener {
            showCustomToast("Empty")
        }
        llEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, email)
            intent.putExtra(Intent.EXTRA_SUBJECT, "Đóng góp ý kiến !")
            try {
                startActivity(Intent.createChooser(intent, "Send email... "))
            } catch (ex: Exception) {
                showCustomToast("Error")
            }
        }
        llRate.setOnClickListener {
            showCustomToast("Empty")
        }

    }

    private fun showCustomToast(message: String) {
        val layout = layoutInflater.inflate(R.layout.layout_custom_toast, rlToastContainer)
        val tvToastMessage = layout.findViewById<AppCompatTextView>(R.id.tvToastMessage)
        tvToastMessage.text = message
        val toast = Toast(context)
        toast.setGravity(Gravity.BOTTOM, 0, 200)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }
    companion object{
        var email = arrayOf<String>("nguyenduyhieuk94@gmail.com")
    }

}
