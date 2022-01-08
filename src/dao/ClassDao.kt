package com.example.dao

import com.example.database.entities.*
import com.example.models.Class
import com.example.util.insertClass
import com.example.database.DatabaseManager
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select

object ClassDao {

    private val database= DatabaseManager().getDatabase()

    fun getClassById(classId: String) =
        Classes.select(Classes.id.eq(classId))

    fun createClass(classI: Class) =
        Classes.insertClass(classI)

    fun getMembersOfClass(classId: String) =
        MemberClass.select(MemberClass.classId.eq(classId))

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
