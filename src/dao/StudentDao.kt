package com.example.dao

import com.example.database.entities.*
import com.example.models.Student
import com.example.util.insertStudent
import com.example.util.toStudent
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class StudentDao {

    fun getAllStudents(): List<Student> =
        transaction {
            Students.selectAll().map {
                it.toStudent()
            }
        }


    fun getStudentById(id: String): Student? =
        transaction {
            Students.select(Students.id.eq(id)).firstOrNull()?.toStudent()
        }

    fun getStudentByNameAndPassword(name:String,password: String) : Student? =
        transaction {
            Students
                .select{
                    Students.name.eq(name) and Students.password.eq(password)
                }
                .firstOrNull()
                ?.toStudent()

        }


    fun createStudent(student: Student) =
        transaction {
            Students.insertStudent(student)
        }



    fun removeStudent(id: String): Boolean =
        transaction {
            Students.deleteWhere { Students.id.eq(id) } == 1
        }


    fun updateStudent(student: Student) =
        transaction {
            Students.update({ Students.id eq student.id }){
                it[name] = student.name
                it[password]= student.password
                it[phone]= student.phone
                it[age]= student.age
                it[note]= student.note
                it[stage]=student.stage
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