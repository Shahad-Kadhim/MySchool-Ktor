package com.example.dao

import com.example.database.entities.*
import com.example.models.School
import com.example.models.UserDto
import com.example.util.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
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

    fun deleteSchool(schoolId: String) =
        transaction{
            Schools.deleteWhere { (Schools.id.eq(schoolId)) }
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



    fun addTeacher(schoolId: String, teacherName: String) =
        transaction{
            teacherDao.getTeacherByName(teacherName)?.let { teacher ->
                TeachersSchool.joinTeacher(schoolId, teacher)
                return@transaction true
            }
            return@transaction null
        }


    fun removeTeacherFromSchool(teacherId: List<String>, schoolId: String) =
        transaction {
            TeachersSchool.deleteWhere {
                ( TeachersSchool.teacherId.inList(teacherId) and TeachersSchool.schoolId.eq(schoolId) )
            }
        }

    fun getTeachers(schoolId: String, searchKey: String?): List<UserDto> =
        transaction {
            teacherDao.getAllTeachers(
                getTeachersInSchool(schoolId),
                searchKey
            )
        }

    private fun getTeachersInSchool(schoolId: String): List<String> =
        transaction{
            TeachersSchool
                .select(TeachersSchool.schoolId.eq(schoolId))
                .map { it[TeachersSchool.teacherId] }
        }

    fun addStudent(schoolId: String,studentName: String) =
        transaction{
            studentDao.getStudentByName(studentName)?.let { student ->
                StudentsSchool.joinStudent(schoolId, student)
                return@transaction true
            }
            return@transaction null
        }



    fun removeStudentFromSchool(studentId: List<String>, schoolId: String) =
        transaction {
            StudentsSchool.deleteWhere {
                ( StudentsSchool.studentId.inList(studentId) and StudentsSchool.schoolId.eq(schoolId) )
            }
        }

    fun getStudents(schoolId: String,searchKey: String?): List<UserDto> =
        transaction {
            studentDao.getAllStudents(getStudentInSchool(schoolId),searchKey)
        }

    private fun getStudentInSchool(schoolId: String): List<String> =
        transaction{
            StudentsSchool
                .select(StudentsSchool.schoolId.eq(schoolId))
                .map { it[StudentsSchool.studentId] }
        }

}
