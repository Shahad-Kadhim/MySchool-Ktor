package com.example.repostiory

import com.example.dao.StudentDao
import com.example.models.Student

class StudentRepository(
    private val studentDao: StudentDao
) {

    fun getAllStudent(): List<Student> =
        studentDao.getAllStudents()


    fun getStudentClasses(studentId: String) =
        studentDao.getStudentClasses(studentId)


}