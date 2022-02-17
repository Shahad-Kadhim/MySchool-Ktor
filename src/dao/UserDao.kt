package com.example.dao

import com.example.authentication.Role
import com.example.authentication.hash
import com.example.database.entities.Mangers
import com.example.database.entities.Students
import com.example.database.entities.Teachers
import com.example.database.entities.Users
import com.example.models.Manger
import com.example.models.Student
import com.example.models.Teacher
import com.example.util.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
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
       ( Users innerJoin Teachers )
           .select { Teachers.id.eq(user.id) }
           .map {
               it.toTeacher()
           }.firstOrNull()



    private fun getMangerInfo(user: User) =
       ( Users innerJoin Mangers )
           .select { Mangers.id.eq(user.id) }
           .map {
               it.toManger()
           }.firstOrNull()


    private fun getStudentInfo(user: User) =
       ( Users innerJoin Students )
           .select { Students.id.eq(user.id) }
           .map {
               it.toStudent()
           }.firstOrNull()



    fun Users.insertUser(user: User){
        this.insert {
            it[id] = user.id
            it[name] = user.name
            it[password] = user.password
            it[phone] = user.phone
            it[role] = user.role.name
        }
    }
    fun ResultRow.toUser()=
        User(
            this[Users.id],
            this[Users.name],
            this[Users.password],
            this[Users.phone],
            this[Users.role].toRole(),
        )

}
fun String.toRole() =
    when(this){
        Role.STUDENT.name -> Role.STUDENT
        Role.TEACHER.name -> Role.TEACHER
        else -> Role.MANGER
    }


data class User(
    val id: String,
    val name: String,
    val password: String,
    val phone: Long,
    val role: Role
)
