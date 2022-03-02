package com.example.dao

import com.example.authentication.Role
import com.example.database.entities.*
import com.example.models.*
import com.example.util.toStudent
import com.example.util.toTeacher
import com.example.util.toTeacherList
import com.example.util.toUserDto
import kotlinx.coroutines.selects.select
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Class

class TeacherDao(
    private val userDao: UserDao
) {

    fun getAllTeachers(): List<TeacherList> =
        transaction {
            (Teachers innerJoin Users)
                .select(Users.role.eq(Role.TEACHER.name))
                .map {
                    it.toTeacherList()
                }
        }

    fun getAllTeachers(teachersId: List<String>,searchKey: String?): List<UserDto> =
        transaction {
            (Teachers innerJoin Users)
                .select(Users.role.eq(Role.TEACHER.name) and Users.id.inList(teachersId) and Users.name.like("${searchKey ?: ""}%"))
                .map {
                    it.toUserDto()
                }
        }

    fun getTeacherById(id: String): Teacher? =
        transaction {
            (Users innerJoin  Teachers)
                .select(Teachers.id.eq(id)).firstOrNull()?.toTeacher()
        }


    fun getTeacherByName(name: String): TeacherList? =
        transaction {
            (Users innerJoin Teachers)
                .select(Users.name.eq(name)).firstOrNull()?.toTeacherList()
        }


    fun getClassesOfTeacher(teacherId: String, searchKey: String?): List<ClassDto> =
        transaction {
            (Teachers innerJoin Classes)
                .select { (Classes.teacherId.eq(teacherId)) and Classes.name.like("${searchKey ?: ""}%")  }
                .map{
                    ClassDto(
                        it[Classes.id],
                        it[Classes.name],
                        getTeacherById(teacherId)?.name ?: "",
                        it[Classes.stage]
                    )
                }
        }

    fun getSchools(id: String): List<SchoolDto> =
        transaction {
            Schools.select(Schools.id.inList(getIdOfTeachersSchool(id))).map {
                SchoolDto(
                    id=it[Schools.id],
                    name = it[Schools.name],
                    mangerName = userDao.findUserById(it[Schools.mangerId])?.name ?: "UNKNOWN"
                )
            }
        }

    private fun getIdOfTeachersSchool(teahcerId: String): List<String> =
        transaction {
            TeachersSchool
                .select(TeachersSchool.teacherId.eq(teahcerId)).map { it[TeachersSchool.schoolId] }
        }
}