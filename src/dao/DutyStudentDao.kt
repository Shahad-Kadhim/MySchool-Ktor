package com.example.dao

import com.example.database.entities.DutyStudent
import com.example.models.Duty
import com.example.models.DutySubmit
import com.example.util.insertDutySolution
import com.example.util.toDutySubmit
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class DutyStudentDao(
    private val userDao: UserDao
) {
    fun addSolution(dutySubmit: DutySubmit) {
        transaction {
            DutyStudent.insertDutySolution(dutySubmit)
        }
    }

    fun getSolutionsForDuty(dutyId: String) =
        transaction {
            DutyStudent.select(DutyStudent.dutyId.eq(dutyId))
                .map{
                    it.toDutySubmit(
                        userDao.findUserById(it[DutyStudent.studentId])?.name ?: ""
                    )
                }
        }

    fun getSolution(dutyId: String,studentId: String) =
        transaction {
            DutyStudent.select(
                DutyStudent.dutyId.eq(dutyId) and
                        DutyStudent.studentId.eq(studentId)
            ).firstOrNull()?.toDutySubmit(
                userDao.findUserById(studentId)?.name ?: ""
            )
        }



}