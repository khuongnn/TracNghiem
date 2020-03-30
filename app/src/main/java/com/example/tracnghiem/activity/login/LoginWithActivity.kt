package com.example.tracnghiem.activity.login

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.tracnghiem.R
import com.example.tracnghiem.base.BaseActivity
import com.example.tracnghiem.databinding.ActivityLoginWithBinding
import com.google.firebase.database.Transaction.success
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import kotlinx.android.synthetic.main.activity_login_with.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class LoginWithActivity : BaseActivity<ActivityLoginWithBinding>() {
    override fun setLayoutId(): Int = R.layout.activity_login_with
    private val mViewModel: LoginViewModel by viewModel()


    override fun initView() {
        Timber.tag(TAG).e("Init view")
    }

    override fun initViewModel() {
        Timber.tag(TAG).e("Init view model")
    }

    override fun initData() {}

    override fun initListener() {


        btnLoginLineTwitter.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>) {
                val session = TwitterCore.getInstance().sessionManager.activeSession
                val authToken = session.authToken
                val token = authToken.token
                val secret = authToken.secret
                loginTwitterUser(session)
            }

            override fun failure(exception: TwitterException) {
                Log.d("twitter error: ","Something went wrong, please try again")
                Toast.makeText(this@LoginWithActivity,"Fail",Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        btnLoginLineTwitter.onActivityResult(requestCode, resultCode, data)
    }

    private fun loginTwitterUser(session: TwitterSession?) : String = session?.userId.toString()




    companion object {
        private const val TAG = "LoginActivity"
    }

}
