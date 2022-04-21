package com.example.di

import com.example.dao.*
import com.example.repostiory.*
import com.example.route.ImageUpload
import com.example.route.LoadImage
import com.google.gson.Gson
import com.transloadit.sdk.Transloadit
import org.koin.dsl.module


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
    single { NotificationDao() }
    single { NotificationRepository(get()) }
}