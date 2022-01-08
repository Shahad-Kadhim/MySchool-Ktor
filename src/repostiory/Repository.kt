package com.example.repostiory

import com.example.dao.ClassDao
import com.example.dao.StudentDao
import com.example.mappers.MemberClassMapper
import com.example.mappers.StudentMapper
import com.example.models.Class
import com.example.models.StudentClasses
import com.example.models.Student

object Repository {
    private val  classDao = ClassDao
    private val studentMapper = StudentMapper()
    private val studentDao= StudentDao()
    private val memberClassMapper =MemberClassMapper()
    fun getAllStudent(): List<Student> =
        studentDao.getAllStudents()

//    fun getStudentClasses(studentId: Long): List<StudentClasses> =
//        studentDao.getStudentClasses(studentId).map {
//            memberClassMapper.map(it)
//        }

    fun getClassById(classId: String)=
        classDao.getClassById(classId)

    fun addStudent(student: Student) =
        studentDao.createStudent(student)

    fun deleteStudent(studentId: String) =
        studentDao.removeStudent(studentId)

    fun getUserByNameAndPassword(name: String, password: String): Student? =
       studentDao.getStudentByNameAndPassword(name,password)


    fun addClass(mClass: Class): Any =
        classDao.createClass(mClass)


    fun getMembers(classId: String) =
        classDao.getMembersOfClass(classId)

}