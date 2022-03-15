package com.example.dao

import com.example.authentication.Role
import com.example.database.entities.*
import com.example.models.SchoolDto
import com.example.models.StudentDto
import com.example.models.UserDto
import com.example.util.toStudent
import com.example.util.toUserDto
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class StudentDao {

    fun getAllStudents(): List<StudentDto> =
        transaction {
            (Students innerJoin Users)
                .select(Users.role.eq(Role.STUDENT.name))
                .map {
                it.toStudent()
            }
        }

    fun getAllStudents(studentsId: List<String>, searchKey: String?): List<UserDto> =
        transaction {
            (Students innerJoin Users)
                .select(Users.role.eq(Role.STUDENT.name) and Users.id.inList(studentsId) and Users.name.like("${searchKey ?: ""}%") )
                .map {
                    it.toUserDto()
                }
        }



    fun getStudentById(id: String): StudentDto? =
        transaction {
            (Users innerJoin Students)
                .select(Students.id.eq(id)).firstOrNull()?.toStudent()
        }

    fun getStudentByName(name: String): StudentDto? =
        transaction {
            (Users innerJoin Students)
                .select(Users.name.eq(name)).firstOrNull()?.toStudent()
        }

    fun getStudentClasses(studentId: String) =
        MemberClass
            .slice(MemberClass.classId ,MemberClass.dateJoined)
            .select{(MemberClass.studentId.eq(studentId))}
            .map {
                it[MemberClass.classId]
            }
    //TODO LATER CHANGE RESPONSE
    fun getSchools(studentId: String) =
       transaction {
            StudentsSchool
                .select { (StudentsSchool.studentId.eq(studentId)) }
                .map {
                    SchoolDto(
                        it[StudentsSchool.schoolId],
                        "",
                        ""
                    )
                }
        }

}