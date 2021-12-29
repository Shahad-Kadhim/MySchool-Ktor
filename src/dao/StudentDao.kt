package com.example.dao

import com.example.database.entities.*
import com.example.models.Student
import com.maaxgr.database.DatabaseManager
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.*

class StudentDao {

    private val Database.student get()= this.sequenceOf(DBStudentTable)
    private val students =  DatabaseManager().getDatabase().student

    fun getAllStudents(): List<DBStudentEntity> =
        students.toList()


    fun getStudentById(id: Long): DBStudentEntity? =
        students.find { it.id eq id }


    fun getStudentByNameAndPassword(name:String,password: String) : DBStudentEntity? =
        students.find{ it.name eq name and ( it.password eq password) }

//
//    fun getStudentClasses(studentId: Long) =
//        database.sequenceOf(DBMemberClassTable).filter { it.studentId eq studentId }.toList()
//


    fun createStudent(student: Student) =
        students.add(DBStudentEntity{
            age =student.age
            password= student.password
            name =student.name
            note= student.note
            stage= student.stage
            phone =student.phone
        }) >= 1



    fun removeStudent(id: Long): Boolean =
        students.removeIf {
            it.id eq id
        } > 0


    fun updateStudent(student: DBStudentEntity) =
        students.update(student)

}