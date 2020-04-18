package com.example.tracnghiem.activity.login

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.tracnghiem.R
import com.example.tracnghiem.activity.login.signup.SignUpActivity
import com.example.tracnghiem.activity.main.HomeActivity
import com.example.tracnghiem.activity.pay.AddPaymentActivity
import com.example.tracnghiem.activity.pay.PayActivity
import com.example.tracnghiem.base.BaseActivity
import com.example.tracnghiem.databinding.ActivityLoginBinding
import com.example.tracnghiem.network.model.response.UserResponse
import com.example.tracnghiem.utils.Constants
import com.example.tracnghiem.utils.hideSoftKeyboard
import com.facebook.AccessToken
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
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_item_login.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.net.HttpURLConnection
import java.util.*

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override fun setLayoutId(): Int = R.layout.activity_login
    private val mViewModel: LoginViewModel by viewModel()
    private var callbackManager = CallbackManager.Factory.create()


    override fun initView() {
        Timber.tag(TAG).e("Init view")
    }

    override fun initViewModel() {
        Timber.tag(TAG).e("Init view model")
    }

    override fun initData() {
        val accessToken = AccessToken.getCurrentAccessToken()
    }

    override fun initListener() {
        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            mainContainer.hideSoftKeyboard(this)
            loginLoad.visibility = View.VISIBLE
            navigationToMainScreen()

//            mViewModel.login(email, password)
//            mViewModel.loginResponseLiveData.observe(this, Observer {
//
//                if (it.data?.code() == HttpURLConnection.HTTP_OK && it.data.body() != null) {
//                    bindData(it.data.body())
//                    navigationToMainScreen()
//                }
//            })

        }

        btnSignUp.setOnClickListener {
            navigationToMainScreen()
        }

        btnLoginTw.setOnClickListener { btnLoginTwitter.performClick() }
        btnLoginTwitter.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>?) {
                val session = TwitterCore.getInstance().sessionManager.activeSession
                val authToken = session.authToken
                val token = authToken.token
                val secret = authToken.secret
                loginTwitterUser(session)
                startActivity(Intent(applicationContext, HomeActivity::class.java))
            }

            override fun failure(exception: TwitterException) {
                Log.d("twitter error: ", "Something went wrong, please try again")
                Toast.makeText(this@LoginActivity, "Login Tw Fail", Toast.LENGTH_LONG).show()
            }

        }


        llFb.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(this, listOf("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult?) {
                        Log.d("MainActivity", "Facebook token: " + result?.accessToken?.token)
                        val accessToken: AccessToken = result!!.accessToken
                        startActivity(Intent(applicationContext, HomeActivity::class.java))
                    }

                    override fun onCancel() {
                        Log.d("MainActivity", "Facebook onCancel.")
                    }

                    override fun onError(error: FacebookException?) {
                        Toast.makeText(this@LoginActivity, "Fail ", Toast.LENGTH_LONG).show()
                    }

                })
        }

        btnLoginLi.setAuthenticationParams(
            LineAuthenticationParams.Builder()
                .scopes(listOf(Scope.PROFILE))
                .build()
        )
        val loginDelegate = LoginDelegate.Factory.create()
        btnLoginLi.setLoginDelegate(loginDelegate)



        btnLoginLi.addLoginListener(object : LoginListener {
            override fun onLoginSuccess(result: LineLoginResult) {
                Toast.makeText(this@LoginActivity, "Login Success: ${result.lineProfile}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("Line Login Success:", "${result.lineProfile}")
            }

            override fun onLoginFailure(result: LineLoginResult?) {
                Toast.makeText(this@LoginActivity, " Line Login Cancel", Toast.LENGTH_SHORT).show()
            }
        })


        btnLoginLine.setOnClickListener { view ->
            try {
                // App-to-app login
                val loginIntent: Intent = LineLoginApi.getLoginIntent(
                    view.context,
                    Constants.CHANNEL_ID,
                    LineAuthenticationParams.Builder()
                        .scopes(listOf(Scope.PROFILE))
                        .build()
                )
                startActivityForResult(loginIntent, 1)
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Fail", Toast.LENGTH_LONG).show()
                Log.e("ERROR", e.toString())
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // FB
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        //TW
        btnLoginTwitter.onActivityResult(requestCode, resultCode, data)

        // Line
        if (requestCode != 1) {
            return }

        val result = LineLoginApi.getLoginResultFromIntent(data)

        when (result.responseCode) {
            LineApiResponseCode.SUCCESS -> {
                // Login successful

                val accessToken = result.lineCredential!!.accessToken.tokenString
                val transitionIntent = Intent(this, HomeActivity::class.java)
                transitionIntent.putExtra("line_profile", result.lineProfile)
                transitionIntent.putExtra("line_credential", result.lineCredential)
                Log.d("Data", "LINE Login " + accessToken)
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


    private fun navigationToMainScreen() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }


    companion object {
        private const val TAG = "LoginActivity"
    }
}


