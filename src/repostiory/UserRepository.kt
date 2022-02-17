package com.example.repostiory

import com.example.dao.UserDao
import com.example.models.Manger
import com.example.models.Student
import com.example.models.Teacher

class UserRepository(
    private val userDao: UserDao
) {
    fun addTeacherUser(teacher: Teacher) =
        userDao.addUserTeacher(teacher)

    fun addMangerUser(manger: Manger) =
        userDao.addUserManger(manger)

    fun addStudentUser(student: Student) =
        userDao.addUserStudent(student)

    fun loginUser(name: String, password: String) =
        userDao.findUserByNameAndPassword(name, password)

    fun addRole(role: String) =
        userDao.addRole(role)

    fun getRoles() =
        userDao.getRoles()
}