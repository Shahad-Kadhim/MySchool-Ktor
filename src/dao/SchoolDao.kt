package com.example.dao

import com.example.database.entities.*
import com.example.models.School
import com.example.models.StudentDto
import com.example.models.TeacherList
import com.example.models.UserDto
import com.example.util.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class SchoolDao(
    private val studentDao: StudentDao,
    private val teacherDao: TeacherDao
) {

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



    fun addTeacher(schoolName: String, teacherName: String) =
        transaction{
            teacherDao.getTeacherByName(teacherName)?.let { teacher ->
                getSchoolByName(schoolName)?.let {
                    TeachersSchool.joinTeacher(it, teacher)
                    return@transaction true
                }
            }
            return@transaction null
        }


    fun getTeachers(schoolId: String): List<UserDto> =
        transaction {
            teacherDao.getAllTeachers(
                getTeachersInSchool(schoolId)
            )
        }

    private fun getTeachersInSchool(schoolId: String): List<String> =
        transaction{
            TeachersSchool
                .select(TeachersSchool.schoolId.eq(schoolId))
                .map { it[TeachersSchool.teacherId] }
        }

    fun addStudent(schoolName: String,studentName: String) =
        transaction{
            studentDao.getStudentByName(studentName)?.let { student ->
                getSchoolByName(schoolName)?.let {
                    StudentsSchool.joinStudent(it, student)
                    return@transaction true
                }
            }
            return@transaction null
        }

    fun getStudents(schoolId: String): List<UserDto> =
        transaction {
            studentDao.getAllStudents(getStudentInSchool(schoolId))
        }

    private fun getStudentInSchool(schoolId: String): List<String> =
        transaction{
            StudentsSchool
                .select(StudentsSchool.schoolId.eq(schoolId))
                .map { it[StudentsSchool.studentId] }
        }

}
