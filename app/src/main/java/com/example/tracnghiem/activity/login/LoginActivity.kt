package com.example.tracnghiem.activity.login

import android.content.Intent
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import com.example.tracnghiem.R
import com.example.tracnghiem.activity.main.HomeActivity
import com.example.tracnghiem.base.BaseActivity
import com.example.tracnghiem.databinding.ActivityLoginBinding
import com.example.tracnghiem.utils.hideSoftKeyboard
import com.example.tracnghiem.utils.hideView
import com.example.tracnghiem.utils.showView
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override fun setLayoutId(): Int = R.layout.activity_login
    private val mViewModel: LoginViewModel by viewModel()


    override fun initView() {
        Timber.tag(TAG).e("Init view")
    }

    override fun initViewModel() {
        Timber.tag(TAG).e("Init view model")
    }

    override fun initData() {}

    override fun initListener() {
        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            mainContainer.hideSoftKeyboard(this)
            loginLoad.visibility = View.VISIBLE
            mViewModel.login(email, password)
        }
        btnLoginWith.setOnClickListener {
            val intent = Intent(this, LoginWithActivity::class.java)
            startActivity(intent)
            finish()

        }
        mViewModel.onLoginSuccess().observe(this, Observer { isSuccess ->
            if (isSuccess) {
                loginLoad.showView()
                Handler().postDelayed({
                    loginLoad.hideView()
                    navigationToMainScreen()
                }, 1000)
            }
        })


    }

    private fun navigationToMainScreen() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}


