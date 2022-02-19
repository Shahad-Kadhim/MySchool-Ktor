package com.example.repostiory

import com.example.dao.SchoolDao
import com.example.models.School
import com.example.models.SchoolDto

class SchoolRepository(
    private val  schoolDao: SchoolDao
) {


    fun getSchoolById(schoolId: String)=
        schoolDao.getSchoolById(schoolId)

    fun addSchool(school: School) =
        schoolDao.createSchool(school)


    fun getClasses(schoolId: String) =
        schoolDao.getClassesInSchool(schoolId)

    fun getAllSchool(): List<School> =
        schoolDao.getAllSchools()

    fun getSchoolByName(schoolName: String): String? =
       schoolDao.getSchoolByName(schoolName)

    fun getSchoolTeacherByNameSchool(schoolName: String, teacherId: String): String? =
        schoolDao.getSchoolTeacherByName(schoolName,teacherId)

    fun addTeacher(schoolId: String, teacherId: String) {
        schoolDao.addTeacher(schoolId,teacherId)
    }

    fun getTeachers(schoolId: String) =
        schoolDao.getTeachers(schoolId)

}