package com.example.di

import com.example.dao.*
import com.example.firebase.FirebaseInterceptor
import com.example.firebase.NotificationService
import com.example.repostiory.*
import com.example.route.ImageUpload
import com.example.route.LoadImage
import com.example.util.DataClassParser
import com.google.gson.Gson
import com.transloadit.sdk.Transloadit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module(createdAtStart = true) {

    single { StudentRepository(get()) }
    single { UserRepository(get()) }
    single { PostRepository(get()) }
    single { MangerRepository(get()) }
    single { ClassRepository(get()) }
    single { DutyRepository(get()) }
    single { TeacherRepository(get())}
    single { SchoolRepository(get()) }
    single { CommentRepository(get()) }
    single { SchoolDao(get(),get(),get()) }
    single { CommentDao(get(),get()) }
    single { StudentDao(get(),get()) }
    single { PostDao(get(),get(),get(),get()) }
    single { TeacherDao(get()) }
    single { ClassDao(get(),get(),get(),get()) }
    single { UserDao() }
    single { DutyStudentDao(get()) }
    single { MangerDao(get(),get()) }
    single { Transloadit(System.getenv("TRANSLOADIT_AUTH_KEY"),System.getenv("TRANSLOADIT_SECRET_KEY")) }
    single { Gson() }
    single { ImageUpload(get()) }
    single { LoadImage(get(),get()) }
    single { NotificationDao(get(),get(),get()) }
    single { NotificationRepository(get()) }
    single { FirebaseInterceptor() }
    single {  HttpLoggingInterceptor().apply{
        level = HttpLoggingInterceptor.Level.BODY
    } }
    factory { Retrofit.Builder()
        .baseUrl("https://fcm.googleapis.com/")
        .client(OkHttpClient.Builder().addInterceptor(get<FirebaseInterceptor>()).addInterceptor(get<HttpLoggingInterceptor>()).build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    }
    single{
        get<Retrofit>().create(NotificationService::class.java)
    }
    single { DataClassParser() }
}