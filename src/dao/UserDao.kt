package com.example.dao

import com.example.authentication.Role
import com.example.database.entities.*
import com.example.models.Manger
import com.example.models.Student
import com.example.models.Teacher
import com.example.models.User
import com.example.util.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class UserDao {

    fun findUserById(id: String): User? =
        transaction{
            Users.select(Users.id.eq(id)).map {
                it.toUser()
            }.firstOrNull()
        }

    fun findUserByNameAndPassword(name: String, password: String): User? =
        transaction{
            Users.select(
                Users.name.eq(name) and
                        Users.password.eq(password)
            ).map {
                it.toUser()
            }.firstOrNull()
        }


    fun addUserTeacher(teacher: Teacher){
        transaction{
            Users.insertUser(User(teacher.id,teacher.name,teacher.password,teacher.phone, Role.TEACHER))
        }
        transaction {
            Teachers.insertTeacher(teacher)
        }
    }

    fun addUserManger(manger: Manger){
        transaction{
            Users.insertUser(
                User(
                    manger.id,
                    manger.name,
                    manger.password,
                    manger.phone,
                    Role.MANGER
                )
            )
            Mangers.insertManger(manger)
        }
    }

    fun addUserStudent(student: Student){
        println(student.toString())
        transaction{
            Users.insertUser(User(student.id,student.name,student.password,student.phone, Role.STUDENT))
        }
        transaction {
            Students.insertStudent(student)
        }
    }

    fun getUserInfoById(id: String): Any? {
     return  findUserById(id)?.let {
         when(it.role){
                Role.TEACHER -> getTeacherInfo(it)
                Role.MANGER -> getMangerInfo(it)
                Role.STUDENT -> getStudentInfo(it)
            }
        }
    }

    private fun getTeacherInfo(user: User) =
        transaction{
            (Users innerJoin Teachers)
                .select { Teachers.id.eq(user.id) }
                .map {
                    it.toTeacher()
                }.firstOrNull()
        }


    private fun getMangerInfo(user: User) =
        transaction{
            (Users innerJoin Mangers)
                .select { Mangers.id.eq(user.id) }
                .map {
                    it.toManger()
                }.firstOrNull()
        }

    private fun getStudentInfo(user: User) =
        transaction{
            (Users innerJoin Students)
                .select { Students.id.eq(user.id) }
                .map {
                    it.toStudent()
                }.firstOrNull()
        }


    fun addRole(role: String) {
        transaction{
            Roles.insert {
                it[Roles.role] = role
            }
        }
    }

    fun getRoles() =
        transaction{
            Roles.selectAll().map { it[Roles.role] }
        }

}
fun String.toRole() =
    when(this){
        Role.STUDENT.name -> Role.STUDENT
        Role.TEACHER.name -> Role.TEACHER
        else -> Role.MANGER
    }

