package com.example.di

import com.example.dao.*
import com.example.repostiory.*
import com.example.route.ImageUpload
import com.google.gson.Gson
import com.transloadit.sdk.Transloadit
import org.koin.core.scope.get
import org.koin.dsl.module


val appModule = module(createdAtStart = true) {

    single { StudentRepository(get()) }
    single { UserRepository(get()) }
    single { PostRepository(get()) }
    single { MangerRepository(get()) }
    single { ClassRepository(get()) }
    single { TeacherRepository(get())}
    single { SchoolRepository(get()) }
    single { CommentRepository(get()) }
    single { SchoolDao(get(),get()) }
    single { CommentDao(get()) }
    single { StudentDao(get()) }
    single { PostDao(get(),get()) }
    single { TeacherDao(get()) }
    single { ClassDao(get()) }
    single { UserDao() }
    single { MangerDao(get(),get()) }
    single { Transloadit(System.getenv("TRANSLOADIT_AUTH_KEY"),System.getenv("TRANSLOADIT_SECRET_KEY")) }
    single { Gson() }
    single { ImageUpload(get()) }
}