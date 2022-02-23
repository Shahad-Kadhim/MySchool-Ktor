package com.example.di

import com.example.dao.*
import com.example.repostiory.*
import org.koin.dsl.module


val appModule = module(createdAtStart = true) {

    single { StudentRepository(get()) }
    single { UserRepository(get()) }
    single { MangerRepository(get(),get()) }
    single { ClassRepository(get()) }
    single { TeacherRepository(get())}
    single { SchoolRepository(get()) }
    single { SchoolDao(get(),get()) }
    single { StudentDao() }
    single { TeacherDao(get()) }
    single { ClassDao(get()) }
    single { UserDao() }
    single { MangerDao(get(),get()) }
}