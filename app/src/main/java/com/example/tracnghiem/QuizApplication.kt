package com.example.tracnghiem

import android.app.Application
import android.util.Log
import com.example.tracnghiem.koindi.myAppMode
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.stripe.android.PaymentConfiguration
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

        PaymentConfiguration.init(
            applicationContext,
            "pk_test_WmIVxUB0YtdM7Ex8qI8J4KQi00uAKS8bzO"
        )
        // Example Tw Auther
        val config = TwitterConfig.Builder(this)
            .logger(DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(TwitterAuthConfig("vZVUNtzpIBLGA6d1ACa9tMW7f", "nd8lJ0jgMNhOxfch6DXKT2TJjYzWPiCkAsOeoxKE7Wi4U1G39X "))
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