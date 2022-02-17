package com.example.dao

import com.example.authentication.Role
import com.example.database.entities.*
import com.example.models.ClassDto
import com.example.models.Teacher
import com.example.util.insertTeacher
import com.example.util.toStudent
import com.example.util.toTeacher
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class TeacherDao {

    fun getAllTeachers(): List<Teacher> =
        transaction {
            (Teachers innerJoin Users)
                .select(Users.role.eq(Role.TEACHER.name))
                .map {
                    it.toTeacher()
                }
        }

    fun createTeacher(teacher: Teacher) =
        transaction {
            Teachers.insertTeacher(teacher)
        }
    fun getTeacherById(id: String): Teacher? =
        transaction {
            Teachers.select(Teachers.id.eq(id)).firstOrNull()?.toTeacher()
        }

//    fun getTeacherByNameAndPassword(name:String,password: String) : Teacher? =
//        transaction {
//            Teachers
//                .select{
//                    Teachers.name.eq(name) and Teachers.password.eq(password)
//                }
//                .firstOrNull()
//                ?.toTeacher()
//        }


    fun removeTeacher(id: String): Boolean =
        transaction {
            Teachers.deleteWhere { Teachers.id.eq(id) } == 1
        }


//    fun updateTeacher(teacher: Teacher) =
//        transaction {
//            Teachers.update({ Teachers.id eq teacher.id }){
//                it[name] = teacher.name
//                it[password]= teacher.password
//                it[phone]= teacher.phone
//                it[teachingSpecialization] = teacher.teachingSpecialization
//            }
//        }


    fun getClasses(id: String): List<ClassDto> =
        transaction {
            (Teachers innerJoin Classes)
                .select { (Classes.teacherId.eq(id)) }
                .map{
                    ClassDto(
                        it[Classes.id],
                        it[Classes.name],
                        getTeacherById(id)?.name ?: ""
                    )
                }
        }


}