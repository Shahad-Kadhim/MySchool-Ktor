package com.example.repostiory

import com.example.dao.ClassDao
import com.example.dao.SchoolDao
import com.example.models.Class
import com.example.models.School

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

    fun addTeacher(schoolId: String, teacherId: String) {
        schoolDao.addTeacher(schoolId,teacherId)
    }

    fun getTeachers(schoolId: String) =
        schoolDao.getTeachers(schoolId)

}