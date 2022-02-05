package com.example.di

import com.example.dao.*
import com.example.repostiory.*
import org.koin.dsl.module


val appModule = module(createdAtStart = true) {

    single { StudentRepository(get()) }
    single { MangerRepository(get()) }
    single { ClassRepository(get()) }
    single { TeacherRepository(get())}
    single { SchoolRepository(get()) }
    single { SchoolDao() }
    single { StudentDao() }
    single { TeacherDao() }
    single { ClassDao() }
    single { MangerDao() }
}