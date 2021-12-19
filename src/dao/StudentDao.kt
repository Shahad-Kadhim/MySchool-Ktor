package com.example.dao

import com.example.database.entities.*
import com.example.models.Student
import com.maaxgr.database.DatabaseManager
import org.ktorm.dsl.*
import org.ktorm.entity.*

class StudentDao {

    private val database= DatabaseManager().getDatabase()
    private val students =  database.sequenceOf(DBStudentTable)

    fun getAllStudents(): List<DBStudentEntity> =
        students.toList()


    fun getStudentById(id: Long): DBStudentEntity? =
        students.find { it.id eq id }


    fun getStudentByNameAndPassword(name:String,password: String) : DBStudentEntity? =
        students.find{ it.name eq name and ( it.password eq password) }


    fun getStudentClasses(studentId: Long) =
        database.sequenceOf(DBMemberClassTable).filter { it.studentId eq studentId }.toList()



    fun createStudent(student: Student) =
        database
            .insertAndGenerateKey(DBStudentTable){
                set(it.age,student.age)
                set(it.password,student.password)
                set(it.name,student.name)
                set(it.note,student.note)
                set(it.stage,student.stage)
                set(it.phone,student.phone)
            }



    fun removeStudent(id: Long): Boolean =
        database.delete(DBStudentTable) {
            it.id eq id
        } > 0


    fun updateStudent(student: DBStudentEntity) =
        database.update(DBStudentTable){
            set(it.age,student.age)
            set(it.password,student.password)
            set(it.name,student.name)
            set(it.note,student.note)
            set(it.stage,student.stage)
            set(it.phone,student.phone)
            where {
                it.id eq student.id
            }
        }

}