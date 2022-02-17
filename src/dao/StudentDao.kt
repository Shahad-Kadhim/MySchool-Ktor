package com.example.dao

import com.example.authentication.Role
import com.example.database.entities.*
import com.example.models.Student
import com.example.util.toStudent
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class StudentDao {

    fun getAllStudents(): List<Student> =
        transaction {
            (Students innerJoin Users)
                .select(Users.role.eq(Role.STUDENT.name))
                .map {
                it.toStudent()
            }
        }


    fun getStudentClasses(studentId: String) =
        MemberClass
            .slice(MemberClass.classId ,MemberClass.dateJoined)
            .select{(MemberClass.studentId.eq(studentId))}
            .map {
                it[MemberClass.classId]
            }

}