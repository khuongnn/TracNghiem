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
import com.example.tracnghiem.base.BaseFragment
import com.example.tracnghiem.databinding.FragmentMyPageBinding
import com.facebook.FacebookSdk.getApplicationContext
import kotlinx.android.synthetic.main.fragment_my_page.*
import kotlinx.android.synthetic.main.layout_custom_toast.*


class MyPageFragment : BaseFragment<FragmentMyPageBinding>() {


    override fun setLayoutId(): Int = R.layout.fragment_my_page
    override fun initViewModel() {

    }

    override fun initData(data: Bundle?) {

    }

    override fun iniListener() {
        llHistory.setOnClickListener {
          //   shareAppWithSocial(getApplicationContext(), LINE_PACKAGE_NAME, "share", "Mota")
        }

        llBookmark.setOnClickListener {
          //   shareAppWithSocial(getApplicationContext(), TWITTER_PACKAGE_NAME, "share", "Mota")
        }

        llChart.setOnClickListener {
        //    shareAppWithSocial(getApplicationContext(), FACEBOOK_PACKAGE_NAME, "share", "Mota")
        }

        llShare.setOnClickListener {
            //  LoginManager.getInstance().logOut()
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

    private fun shareAppWithSocial(context: Context, application: String?, title: String?, description: String?) {
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
        var email = arrayOf<String>("nguyenduyhieuk94@gmail.com")
        const val FACEBOOK_PACKAGE_NAME = "com.facebook.katana"
        const val TWITTER_PACKAGE_NAME = "com.twitter.android"
        const val LINE_PACKAGE_NAME = "jp.naver.line.android"
    }

}
