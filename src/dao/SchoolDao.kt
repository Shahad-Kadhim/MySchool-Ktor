package com.example.dao

import com.example.database.entities.*
import com.example.models.School
import com.example.models.Student
import com.example.util.insertSchool
import com.example.util.toSchool
import com.example.util.toStudent
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class SchoolDao {

    fun getAllSchools(): List<School> =
        transaction {
            Schools.selectAll().map {
                it.toSchool()
            }
        }

    fun getSchoolById(schoolId: String) =
       transaction {
           Schools.select(Schools.id.eq(schoolId)).map {
               it.toSchool()
           }.firstOrNull()
       }

    fun createSchool(school: School) =
        transaction {
            Schools.insertSchool(school)

        }
    fun getClassesInSchool(SchoolId: String) =
        transaction {
            (Schools innerJoin Classes)
                .slice(Classes.id)
                .select { (Schools.id.eq(Classes.schoolId)) }
                .map{
                    it[Classes.id]
                }
        }
}
