package com.example.dao

import com.example.database.entities.*
import com.example.models.Class
import com.example.models.Student
import com.example.models.UserDto
import com.example.util.insertClass
import com.example.util.joinStudent
import com.example.util.toClass
import com.example.util.toStudent
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class ClassDao(
    private val studentDao: StudentDao
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

    fun getStudentInClass(classId: String):List<UserDto> =
        transaction {
            studentDao.getAllStudents(getMembersOfClass(classId))
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

}
