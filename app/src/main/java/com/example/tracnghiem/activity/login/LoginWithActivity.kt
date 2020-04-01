package com.example.tracnghiem.activity.login

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.example.tracnghiem.R
import com.example.tracnghiem.activity.main.HomeActivity
import com.example.tracnghiem.base.BaseActivity
import com.example.tracnghiem.databinding.ActivityLoginWithBinding
import com.example.tracnghiem.utils.Constants
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.linecorp.linesdk.LineApiResponseCode
import com.linecorp.linesdk.LoginDelegate
import com.linecorp.linesdk.LoginListener
import com.linecorp.linesdk.Scope
import com.linecorp.linesdk.auth.LineAuthenticationParams
import com.linecorp.linesdk.auth.LineLoginApi
import com.linecorp.linesdk.auth.LineLoginResult
import com.twitter.sdk.android.core.*
import kotlinx.android.synthetic.main.activity_login_with.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*


class LoginWithActivity : BaseActivity<ActivityLoginWithBinding>() {
    override fun setLayoutId(): Int = R.layout.activity_login_with
    private val mViewModel: LoginViewModel by viewModel()

    private var callbackManager: CallbackManager? = null

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
                Log.d("twitter error: ", "Something went wrong, please try again")
                Toast.makeText(this@LoginWithActivity, "Fail", Toast.LENGTH_LONG).show()
            }

        }

        btnLoginFaceBook.setOnClickListener {
            //login
            LoginManager.getInstance()
                .logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult?) {
                        Log.d("MainActivity", "Facebook token: " + result?.accessToken?.token)
                        startActivity(Intent(applicationContext, HomeActivity::class.java))
                    }

                    override fun onCancel() {
                        Log.d("MainActivity", "Facebook onCancel.")
                    }

                    override fun onError(error: FacebookException?) {
                        Log.d("MainActivity", "Facebook onError.")
                    }

                })

        }


        btnLoginLine.setChannelId("the channel id you created")
        btnLoginLine.enableLineAppAuthentication(true)
        // set up required scopes.

        // set up required scopes.
        btnLoginLine.setAuthenticationParams(
            LineAuthenticationParams.Builder()
                .scopes(Arrays.asList(Scope.PROFILE))
                .build()
        )
        val loginDelegate = LoginDelegate.Factory.create()
        btnLoginLine.setLoginDelegate(loginDelegate)

//        btnLoginLine.addLoginListener(object : LoginListener {
//            override fun onLoginSuccess(@NonNull result: LineLoginResult) {
//                Toast.makeText(this@LoginWithActivity, "Login success", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onLoginFailure(@Nullable result: LineLoginResult?) {
//                Toast.makeText(this@LoginWithActivity, "Login failure", Toast.LENGTH_SHORT).show()
//            }
//        })
        btnLoginLine.setOnClickListener { view ->
            try {
                // App-to-app login
                val loginIntent: Intent = LineLoginApi.getLoginIntent(
                    view.context,
                    Constants.CHANNEL_ID,
                    LineAuthenticationParams.Builder()
                        .scopes(Arrays.asList(Scope.PROFILE)) // .nonce("<a randomly-generated string>") // nonce can be used to improve security
                        .build()
                )
                startActivityForResult(loginIntent, 1)
            } catch (e: Exception) {
                Toast.makeText(this@LoginWithActivity, "Fail", Toast.LENGTH_LONG).show()
                Log.e("ERROR", e.toString())
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        btnLoginLineTwitter.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        if (requestCode != 1) {
            Toast.makeText(this@LoginWithActivity, "Fail", Toast.LENGTH_LONG).show()
            return
        }

        val result = LineLoginApi.getLoginResultFromIntent(data)

        when (result.responseCode) {
            LineApiResponseCode.SUCCESS -> {
                // Login successful
                val accessToken =
                    result.lineCredential!!.accessToken.tokenString
                val transitionIntent = Intent(this, HomeActivity::class.java)
                transitionIntent.putExtra("line_profile", result.lineProfile)
                transitionIntent.putExtra("line_credential", result.lineCredential)
                startActivity(transitionIntent)
            }
            LineApiResponseCode.CANCEL ->             // Login canceled by user
                Log.e("ERROR", "LINE Login Canceled by user.")
            else -> {
                // Login canceled due to other error
                Log.e("ERROR", "Login FAILED!")
                Log.e("ERROR", result.errorData.toString())
            }
        }
    }

    private fun loginTwitterUser(session: TwitterSession?): String = session?.userId.toString()


    companion object {
        private const val TAG = "LoginActivity"
    }

}
