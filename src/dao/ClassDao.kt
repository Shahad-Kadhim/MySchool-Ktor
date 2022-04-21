package com.example.dao

import com.example.database.entities.*
import com.example.models.Class
import com.example.models.Notification
import com.example.models.UserDto
import com.example.util.insertClass
import com.example.util.joinStudent
import com.example.util.toClass
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.notInList
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class ClassDao(
    private val studentDao: StudentDao,
    private val notificationDao: NotificationDao,
    private val schoolDao: SchoolDao,
    private val userDao: UserDao
) {

    fun getAllClasses(): List<Class> =
        transaction {
            Classes.selectAll().map {
                it.toClass()
            }
        }

    fun getClassById(classId: String) =
       transaction {
           Classes.select(Classes.id.eq(classId)).map {
               it.toClass()
           }.firstOrNull()
       }

    fun createClass(classI: Class) =
        transaction {
            Classes.insertClass(classI)
            createNotificationToCreateClass(classI)
        }

    private fun createNotificationToCreateClass(classI: Class){
        schoolDao.getSchoolById(classI.schoolId)?.let {
            notificationDao.createNotification(
                Notification(
                    id = UUID.randomUUID().toString(),
                    title = "New class created",
                    content = "the ${userDao.findUserById(classI.teacherId)?.name} create ${classI.name}",
                    date = Date().time
                ),
                listOf(it.mangerId)
            )
        }
    }

    fun deleteClass(classId: String) =
        transaction{
            Classes.deleteWhere { (Classes.id.eq(classId)) }
        }


    //TODO REMOVE INITIAL VALUE
    fun getStudentInClass(classId: String,searchKey: String? =null):List<UserDto> =
        transaction {
            studentDao.getAllStudents(getMembersOfClass(classId),searchKey)
        }

    private fun getMembersOfClass(classId: String) =
        transaction {
            MemberClass
                .slice(MemberClass.studentId)
                .select(MemberClass.classId.eq(classId))
                .map {
                    it[MemberClass.studentId]
                }
        }

    fun addStudentsInClass(studentsId :List<String>,classId: String) =
        transaction {
            MemberClass.joinStudent(studentsId,classId)
            getClassById(classId)?.let {
                createNotificationToJoinToCLass(studentsId, it)
            }
        }

    private fun createNotificationToJoinToCLass(studentsId: List<String>, classI: Class){
        notificationDao.createNotification(
            Notification(
                id = UUID.randomUUID().toString(),
                title = "You Joined To New Class",
                content = "the ${userDao.findUserById(classI.teacherId)} add You to ${classI.name}",
                date = Date().time
            ),
            studentsId
        )
    }

    fun removeStudentFromClass(studentId: List<String>, classId: String){
        transaction {
            MemberClass.deleteWhere { (MemberClass.studentId.inList(studentId) and MemberClass.classId.eq(classId)) }
        }
    }

    fun getStudentInSchoolToAddToClass(classId: String): List<UserDto>? =
        getClassById(classId)?.let {
            getStudentsNotInClass(it)
        }


    private fun getStudentsNotInClass(mClass: Class): List<UserDto> =
        transaction {
            studentDao.getAllStudents(
                StudentsSchool
                    .select(StudentsSchool.schoolId.eq(mClass.schoolId) and (StudentsSchool.studentId.notInList(getMembersOfClass(mClass.id))))
                    .map { it[StudentsSchool.studentId] },
                null
            )
        }

}
