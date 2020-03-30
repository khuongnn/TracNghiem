package com.example.tracnghiem.koindi

import com.example.tracnghiem.activity.login.LoginViewModel
import com.example.tracnghiem.activity.main.course.CourseViewModel
import com.example.tracnghiem.activity.main.course.chapter.CourseChapterViewModel
import com.example.tracnghiem.activity.main.course.lesson.CourseLessonViewModel
import com.example.tracnghiem.activity.main.mainpractice.TrainViewModel
import com.example.tracnghiem.activity.main.practice.PracticeViewModel
import com.example.tracnghiem.network.NetworkManager
import com.example.tracnghiem.network.intercepter.QuizInterceptor
import com.example.tracnghiem.network.services.QuizApiService
import com.example.tracnghiem.network.services.QuizApiServiceImpl
import com.example.tracnghiem.utils.PreferencesHelper
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single { PreferencesHelper(get()) }
    single {
        // Webservice
        createWebService<QuizApiService>(
            createOkHttpClient(QuizInterceptor()), "https://www.google.com.vn/"
        )
    }
}
val dataModule = module {
    single { QuizApiServiceImpl(get()) }
    factory { NetworkManager(get()) }
    // view  model class
    viewModel { LoginViewModel() }
    viewModel { CourseViewModel() }
    viewModel { TrainViewModel() }
    viewModel { PracticeViewModel() }
    viewModel { CourseChapterViewModel() }
    viewModel { CourseLessonViewModel() }
}


fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    val timeout = 30L

    return OkHttpClient.Builder()
        .connectTimeout(timeout, TimeUnit.SECONDS)
        .readTimeout(timeout, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val gsonBuilder = GsonBuilder().setLenient().create()
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}

val myAppMode = listOf(
    appModule,
    dataModule
)