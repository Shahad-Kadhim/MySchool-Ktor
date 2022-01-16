package com.example.repostiory

import dao.StudentDao
import com.example.models.Student

class StudentRepository(
    private val studentDao: StudentDao
) {

    fun getAllStudent(): List<Student> =
        studentDao.getAllStudents()


    fun getStudentClasses(studentId: String) =
        studentDao.getStudentClasses(studentId)


    fun addStudent(student: Student) =
        studentDao.createStudent(student)

    fun deleteStudent(studentId: String) =
        studentDao.removeStudent(studentId)

    fun getUserByNameAndPassword(name: String, password: String): Student? =
       studentDao.getStudentByNameAndPassword(name,password)


}