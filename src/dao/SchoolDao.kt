package com.example.dao

import com.example.database.entities.*
import com.example.models.School
import com.example.models.Teacher
import com.example.models.TeacherList
import com.example.util.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class SchoolDao {

    fun getAllSchools(): List<School> =
        transaction {
            Schools.selectAll().map {
                it.toSchool()
            }
        }

    fun getSchoolById(schoolId: String) =
       transaction {
           Schools.select(Schools.id.eq(schoolId)).map {
               it.toSchool()
           }.firstOrNull()
       }

    fun createSchool(school: School) =
        transaction {
            Schools.insertSchool(school)

        }
    fun getClassesInSchool(SchoolId: String) =
        transaction {
            (Schools innerJoin Classes)
                .slice(Classes.id)
                .select { (Schools.id.eq(Classes.schoolId)) }
                .map{
                    it[Classes.id]
                }
        }

    fun getSchoolByName(schoolName: String): String? =
        transaction {
            Schools.select(Schools.name.eq(schoolName))
                .map { it[Schools.id] }.firstOrNull()
        }

    fun getSchoolTeacherByName(schoolName: String ,teacherId: String) =
        transaction {
            getSchoolByName(schoolName)?.takeIf {
                TeachersSchool
                    .select(TeachersSchool.teacherId.eq(teacherId))
                    .map { it[TeachersSchool.schoolId] }.contains(it)
            }?.let {
                it
            }
        }



    fun addTeacher(schoolId: String, teacherId: String) {
        transaction {
            TeachersSchool.joinTeacher(schoolId,teacherId)
        }
    }

    fun getTeachers(schoolId: String): List<TeacherList> =
        transaction {
            Teachers.select(Teachers.id.inList(
                TeachersSchool
                    .select(TeachersSchool.schoolId.eq(schoolId))
                    .map { it[TeachersSchool.teacherId] }
            )).map {
                it.toTeacherList()
            }
        }



}
