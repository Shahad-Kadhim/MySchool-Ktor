package com.example.dao

import com.example.authentication.Role
import com.example.database.entities.*
import com.example.models.*
import com.example.util.toTeacher
import com.example.util.toTeacherList
import com.example.util.toUserDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.transactions.transaction

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

    fun getAllTeachers(teachersId: List<String>): List<UserDto> =
        transaction {
            (Teachers innerJoin Users)
                .select(Users.role.eq(Role.TEACHER.name) and Users.id.inList(teachersId))
                .map {
                    it.toUserDto()
                }
        }

    fun getTeacherById(id: String): Teacher? =
        transaction {
            (Users innerJoin  Teachers)
                .select(Teachers.id.eq(id)).firstOrNull()?.toTeacher()
        }

    fun getClasses(id: String): List<ClassDto> =
        transaction {
            (Teachers innerJoin Classes)
                .select { (Classes.teacherId.eq(id)) }
                .map{
                    ClassDto(
                        it[Classes.id],
                        it[Classes.name],
                        getTeacherById(id)?.name ?: "",
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