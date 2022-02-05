package com.example.repostiory

import com.example.dao.TeacherDao
import com.example.models.Teacher

class TeacherRepository(
    private val teacherDao: TeacherDao
) {



    fun addTeacher(teacher: Teacher){
        teacherDao.createTeacher(teacher)
    }

    fun getTeacherById(id: String)=
        teacherDao.getTeacherById(id)

    fun deleteTeacher(id: String) =
        teacherDao.removeTeacher(id)

    fun getTeacherByNameAndPassword(name: String, password: String): Teacher? =
        teacherDao.getTeacherByNameAndPassword(name,password)


    fun getTeacherClasses(id: String)=
        teacherDao.getClasses(id)

    fun getAllTeacher(): List<Teacher> =
        teacherDao.getAllTeachers()


}