package com.example.dao

import com.example.database.entities.*
import com.example.models.Manger
import com.example.models.Student
import com.example.models.Teacher
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


    fun getSchools(id: String): List<String> =
        transaction {
            (Mangers innerJoin Schools)
                .slice(Schools.id)
                .select { (Schools.mangerId.eq(Mangers.id)) }
                .map{
                    it[Schools.id]
                }
        }


}