package com.example.repostiory

import com.example.dao.TeacherDao
import com.example.models.Teacher
import com.example.models.TeacherList

class TeacherRepository(
    private val teacherDao: TeacherDao
) {

    fun getTeacherInfo(id: String)=
        teacherDao.getTeacherById(id)

    fun getTeacherClasses(id: String,searchKey: String?)=
        teacherDao.getClassesOfTeacher(id, searchKey)

    fun getAllTeacher(): List<TeacherList> =
        teacherDao.getAllTeachers()

    fun getTeacherSchools(teacherId: String) =
        teacherDao.getSchools(teacherId)
}