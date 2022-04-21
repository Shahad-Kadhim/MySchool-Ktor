package com.example.dao

import com.example.database.entities.*
import com.example.models.Notification
import com.example.models.School
import com.example.models.UserDto
import com.example.util.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import java.util.Date

class SchoolDao(
    private val studentDao: StudentDao,
    private val teacherDao: TeacherDao,
    private val notificationDao: NotificationDao
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

    fun getClassesInSchool(schoolId: String) =
        transaction {
            (Schools innerJoin Classes)
                .slice(Classes.id)
                .select { (Classes.schoolId.eq(schoolId))}
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
                createNotificationToJoinSchool(teacher.id)
                return@transaction true
            }
            return@transaction null
        }


    fun removeTeacherFromSchool(teacherId: List<String>, schoolId: String) =
        transaction {
            TeachersSchool.deleteWhere {
                ( TeachersSchool.teacherId.inList(teacherId) and TeachersSchool.schoolId.eq(schoolId) )
            }.takeIf { it >0  }?.let {
                createNotificationToRemoveFromSchool(teacherId)
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
                createNotificationToJoinSchool(student.id)
                return@transaction true
            }
            return@transaction null
        }


    private fun createNotificationToJoinSchool(userId: String){
        notificationDao.createNotification(
            Notification(
                id = UUID.randomUUID().toString(),
                title = "You join to New School",
                content = "the manger add you to school",
                date = Date().time
            ),
            listOf(userId)
        )
    }

    private fun createNotificationToRemoveFromSchool(users: List<String>){
        notificationDao.createNotification(
            Notification(
                id = UUID.randomUUID().toString(),
                title = "You Remove From your School",
                content = "the manger remove you from school",
                date = Date().time
            ),
            users
        )
    }

    fun removeStudentFromSchool(studentId: List<String>, schoolId: String) =
        transaction {
            StudentsSchool.deleteWhere {
                ( StudentsSchool.studentId.inList(studentId) and StudentsSchool.schoolId.eq(schoolId) )
            }.takeIf { it >0 }?.let {
                createNotificationToRemoveFromSchool(studentId)
            }
        }

    fun getStudents(schoolId: String,searchKey: String?=null): List<UserDto> =
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
