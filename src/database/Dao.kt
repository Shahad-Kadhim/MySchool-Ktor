package com.example.database

import com.maaxgr.database.DatabaseManager
import com.example.models.Student
import org.ktorm.dsl.*
import org.ktorm.entity.*

class Dao {

    private val database= DatabaseManager().getDatabase()

    fun getAllStudents(): List<DBStudentEntity> {
        return database.sequenceOf(DBStudentTable).toList()
    }

    fun getStudent(id: String): DBStudentEntity? {
        return database.sequenceOf(DBStudentTable)
            .firstOrNull { it.id eq id }
    }

    fun getStudentByNameAndPassword(name:String,password: String) :DBStudentEntity? =
        database.sequenceOf(DBStudentTable).firstOrNull{ it.name eq name and ( it.password eq password) }



    fun addStudent(student: Student):Boolean{

        database.insert(DBStudentTable){
            set(DBStudentTable.id,((1..1000).random()).toString())
            set(DBStudentTable.age,student.age)
            set(DBStudentTable.password,student.password)
            set(DBStudentTable.name,student.name)
            set(DBStudentTable.note,student.note)
            set(DBStudentTable.stage,student.stage)
            set(DBStudentTable.phone,student.phone)
        }
        return true
    }


    fun removeStudent(id: String): Boolean {
        val deletedRows = database.delete(DBStudentTable) {
            it.id eq id
        }
        return deletedRows > 0
    }


}