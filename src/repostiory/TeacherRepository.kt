package com.example.repostiory

import com.example.dao.TeacherDao
import com.example.models.Teacher

class TeacherRepository(
    private val teacherDao: TeacherDao
) {

    fun getTeacherById(id: String)=
        teacherDao.getTeacherById(id)

    fun getTeacherClasses(id: String)=
        teacherDao.getClasses(id)

    fun getAllTeacher(): List<Teacher> =
        teacherDao.getAllTeachers()


}