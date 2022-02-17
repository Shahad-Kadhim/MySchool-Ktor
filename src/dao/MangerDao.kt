package com.example.dao

import com.example.authentication.Role
import com.example.database.entities.*
import com.example.models.*
import com.example.util.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class MangerDao {

    fun getAllMangers(): List<Manger> =
        transaction {
            (Mangers innerJoin Users)
                .select(Users.role.eq(Role.MANGER.name))
                .map {
                    it.toManger()
                }
        }



    fun getSchools(id: String): List<School> =
        transaction {
            (Mangers innerJoin Schools)
                .select { (Schools.mangerId.eq(id)) }
                .map{
                    School(it[Schools.id],it[Schools.name],id)
                }
        }
//TODO Uncomment Later
    fun getClasses(id: String): List<ClassDto> =
        transaction {
            (Schools innerJoin Classes)
                .select {
                    Classes.schoolId.inList(getSchools(id).map { it.id })
                }.map {
                    ClassDto(
                        it[Classes.id],
                        it[Classes.name],
                        ""
//                        Teachers.select(Teachers.id.eq(it[Classes.teacherId])).map {it[Teachers.name]}.firstOrNull() ?: ""
                    )
                }
        }


}