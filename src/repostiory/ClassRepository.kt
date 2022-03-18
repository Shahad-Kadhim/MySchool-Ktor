package com.example.repostiory

import com.example.dao.ClassDao
import com.example.models.Class
import com.example.models.UserDto

class ClassRepository(
    private val  classDao: ClassDao
) {

    fun getClassById(classId: String)=
        classDao.getClassById(classId)

    fun addClass(mClass: Class) =
        classDao.createClass(mClass)

    fun removeStudent(studentId:List<String>, classId: String) =
        classDao.removeStudentFromClass(studentId,classId)

    fun getMembers(classId: String) =
        classDao.getStudentInClass(classId)

    fun getAllClasses(): List<Class> =
        classDao.getAllClasses()

    fun addStudentToClass(studentsId: List<String>, classId: String) =
        classDao.addStudentsInClass(studentsId,classId)

    fun getStudentInSchoolToAddToClass(classId: String): List<UserDto>? =
        classDao.getStudentInSchoolToAddToClass(classId)
}