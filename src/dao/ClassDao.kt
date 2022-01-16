package com.example.dao

import com.example.database.entities.*
import com.example.models.Class
import com.example.util.insertClass
import com.example.util.toClass
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select

class ClassDao {

    fun getClassById(classId: String) =
        Classes.select(Classes.id.eq(classId)).map {
            it.toClass()
        }.firstOrNull()

    fun createClass(classI: Class) =
        Classes.insertClass(classI)

    fun getMembersOfClass(classId: String) =
        MemberClass
            .slice(MemberClass.studentId)
            .select(MemberClass.classId.eq(classId))
            .map {
                it[MemberClass.studentId]
            }

}
