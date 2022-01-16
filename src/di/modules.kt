package com.example.di

import com.example.dao.ClassDao
import com.example.dao.TeacherDao
import com.example.repostiory.ClassRepository
import com.example.repostiory.StudentRepository
import com.example.repostiory.TeacherRepository
import dao.StudentDao
import org.koin.dsl.module


val appModule = module(createdAtStart = true) {

    single { StudentRepository(get()) }
    single { ClassRepository(get()) }
    single { TeacherRepository(get())}
    single { StudentDao() }
    single { TeacherDao() }
    single { ClassDao() }
}