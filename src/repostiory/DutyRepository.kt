package com.example.repostiory

import com.example.dao.DutyStudentDao
import com.example.models.DutySubmit

class DutyRepository(
    private val dutyStudentDao: DutyStudentDao
) {

    fun addSolution(dutySubmit: DutySubmit) =
        dutyStudentDao.addSolution(dutySubmit)

    fun getSolutionsForDuty(dutyId: String) =
        dutyStudentDao.getSolutionsForDuty(dutyId)

    fun getSolution(dutyId: String, studentId: String) =
        dutyStudentDao.getSolution(dutyId, studentId)
}