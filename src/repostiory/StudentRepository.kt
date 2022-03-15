package com.example.repostiory

import com.example.dao.StudentDao
import com.example.models.Student
import com.example.models.StudentDto

class StudentRepository(
    private val studentDao: StudentDao
) {

    fun getAllStudent(): List<StudentDto> =
        studentDao.getAllStudents()


    fun getStudentClasses(studentId: String) =
        studentDao.getStudentClasses(studentId)

    fun getStudentInfo(studentId: String) =
        studentDao.getStudentById(studentId)

    fun getStudentSchool(studentId: String) =
        studentDao.getSchools(studentId)
}