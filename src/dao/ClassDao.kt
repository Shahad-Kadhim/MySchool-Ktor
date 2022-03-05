package com.example.dao

import com.example.database.entities.*
import com.example.models.Class
import com.example.models.UserDto
import com.example.util.insertClass
import com.example.util.joinStudent
import com.example.util.toClass
import com.example.models.UserSelected
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.notInList
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class ClassDao(
    private val studentDao: StudentDao,
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
        }

    //TODO REMOVE INITIAL VALUE
    fun getStudentInClass(classId: String,searchKey: String? =null):List<UserSelected> =
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
        }

    fun getStudentInSchoolToAddToClass(classId: String): List<UserSelected>? =
        getClassById(classId)?.let {
            getStudentsNotInClass(it)
        }


    private fun getStudentsNotInClass(mClass: Class): List<UserSelected> =
        transaction {
            studentDao.getAllStudents(
                StudentsSchool
                    .select(StudentsSchool.schoolId.eq(mClass.schoolId) and (StudentsSchool.studentId.notInList(getMembersOfClass(mClass.id))))
                    .map { it[StudentsSchool.studentId] },
                null
            )
        }
}
