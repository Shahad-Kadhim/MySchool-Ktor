package com.example.dao

import com.example.database.entities.*
import com.example.models.*
import com.example.util.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class MangerDao {

    fun getAllMangers(): List<Manger> =
        transaction {
            Mangers.selectAll().map {
                it.toManger()
            }
        }

    fun createManger(manger: Manger) =
        transaction {
            Mangers.insertManger(manger)
        }
    fun getMangerById(id: String): Manger? =
        transaction {
            Mangers.select(Mangers.id.eq(id)).firstOrNull()?.toManger()
        }

    fun getMangerByNameAndPassword(name:String,password: String) : Manger? =
        transaction {
            Mangers
                .select{
                    Mangers.name.eq(name) and Mangers.password.eq(password)
                }
                .firstOrNull()
                ?.toManger()
        }


    fun removeManger(id: String): Boolean =
        transaction {
            Mangers.deleteWhere { Mangers.id.eq(id) } == 1
        }


    fun updateManger(Manger: Manger) =
        transaction {
            Mangers.update({ Mangers.id eq Manger.id }){
                it[name] = Manger.name
                it[password]= Manger.password
            }
        }


    fun getSchools(id: String): List<School> =
        transaction {
            (Mangers innerJoin Schools)
                .select { (Schools.mangerId.eq(id)) }
                .map{
                    School(it[Schools.id],it[Schools.name],it[Schools.mangerId])
                }
        }

    fun getClasses(id: String): List<ClassDto> =
        transaction {
            (Schools innerJoin Classes)
                .select {
                    Classes.schoolId.inList(getSchools(id).map { it.id })
                }.map {
                    ClassDto(
                        it[Classes.id],
                        it[Classes.name],
                        Teachers.select(Teachers.id.eq(it[Classes.teacherId])).map {it[Teachers.name]}.firstOrNull() ?: ""
                    )
                }
        }


}