package com.example.dao

import com.example.database.entities.*
import com.example.models.Class
import com.maaxgr.database.DatabaseManager
import org.ktorm.dsl.*
import org.ktorm.entity.*

object ClassDao {

    private val database= DatabaseManager().getDatabase()

    fun getClassById(classId: Long) =
        database.sequenceOf(DBClassTable).find { DBClassTable.id eq classId }

    fun createClass(classI: Class) =
        database
            .insertAndGenerateKey(DBClassTable){
                set(DBClassTable.name,classI.name)
                set(DBClassTable.schoolId,classI.schoolId)
                set(DBClassTable.teacherId,classI.teacherId)
                set(DBClassTable.stage,classI.stage)
            }


    fun getMembersOfClass(classId: Long) =
        database.sequenceOf(DBMemberClassTable).filter { it.classId eq classId }


}












//
//private fun DBClassEntity.toClassModel() =
//    Class(
//        this.id,
//        this.name,
//        this.teacherId.id,
//        this.schoolId.id,
//        this.stage
//    )
//
//private fun DBStudentEntity.toClassModel() =
//    Student(
//        this.id,
//        this.name,
//        this.teacherId.id,
//        this.schoolId.id,
//        this.stage
//    )
