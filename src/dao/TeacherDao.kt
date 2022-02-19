package com.example.dao

import com.example.authentication.Role
import com.example.database.entities.*
import com.example.models.ClassDto
import com.example.models.School
import com.example.models.Teacher
import com.example.util.insertTeacher
import com.example.util.toSchool
import com.example.util.toStudent
import com.example.util.toTeacher
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
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

    fun getSchools(id: String): List<School> =
        transaction {
            Schools.select(Schools.id.inList(getIdOfTeachersSchool(id))).map {
                it.toSchool()
            }
        }

    private fun getIdOfTeachersSchool(teahcerId: String): List<String> =
        transaction {
            TeachersSchool
                .select(TeachersSchool.teacherId.eq(teahcerId)).map { it[TeachersSchool.schoolId] }
        }
}