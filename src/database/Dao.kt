package com.example.database

import com.example.database.entities.DBClassTable
import com.example.database.entities.DBMemberClassTable
import com.example.database.entities.DBStudentEntity
import com.example.database.entities.DBStudentTable
import com.example.models.Class
import com.example.models.MemberClass
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

    fun getStudentByNameAndPassword(name:String,password: String) : DBStudentEntity? =
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

    fun getStudentClasses(studentId: String) =
        database.sequenceOf(DBMemberClassTable).map { MemberClass(it.classId,it.dateJoined) }

    fun getClassById(classId: String)=
        database.from(DBClassTable).select().where { DBClassTable.id eq classId }.map {
            Class(
               name ="${it[DBClassTable.name]}",
               teacherId = "${it[DBClassTable.teacherId]}",
               schoolId = "${it[DBClassTable.schoolId]}",
               stage = "${it[DBClassTable.stage]}".toIntOrNull(),
            )
        }.firstOrNull()

    fun insertClass(classI: Class): Int {
        return database.insert(DBClassTable){
            set(DBClassTable.id,"${(1..39998).random()}")
            set(DBClassTable.name,classI.name)
            set(DBClassTable.schoolId,classI.schoolId)
            set(DBClassTable.teacherId,classI.teacherId)
            set(DBClassTable.stage,classI.stage)
        }
    }

    fun getMembers(classId: String) =
        database.from(DBMemberClassTable).select().where { DBMemberClassTable.classId eq classId }.map {
            it[DBMemberClassTable.studentId]
        }.filterNotNull()


}