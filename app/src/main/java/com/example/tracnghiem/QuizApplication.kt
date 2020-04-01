package com.example.tracnghiem

import android.app.Application
import android.util.Log
import com.example.tracnghiem.koindi.myAppMode
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class QuizApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        // Example Tw Auther
        val config = TwitterConfig.Builder(this)
            .logger(DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(TwitterAuthConfig("r5nPFPbcDrzoJM9bIBCqyfHPK", "oJ8y2KPIySPpoBX3eCcqgcnmPGXLI94BR4g9ZztnApSmXQG9Ij "))
            .debug(true)
            .build()
        Twitter.initialize(config)

        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@QuizApplication)
            modules(myAppMode)
        }
    }
}