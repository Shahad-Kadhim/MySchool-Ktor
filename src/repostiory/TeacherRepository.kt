package com.example.repostiory

import com.example.dao.TeacherDao
import com.example.models.Student
import com.example.models.Teacher

class TeacherRepository {

    val dao = TeacherDao()

    fun addTeacher(teacher: Teacher){
        dao.createTeacher(teacher)
    }

    fun getTeacherById(id: String)=
        dao.getTeacherById(id)

    fun deleteTeacher(id: String) =
        dao.removeTeacher(id)

    fun getTeacherByNameAndPassword(name: String, password: String): Teacher? =
        dao.getTeacherByNameAndPassword(name,password)



}