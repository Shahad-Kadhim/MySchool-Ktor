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

    fun deleteSchool(schoolId: String) =
        schoolDao.deleteSchool(schoolId)

    fun getClasses(schoolId: String) =
        schoolDao.getClassesInSchool(schoolId)

    fun getAllSchool(): List<School> =
        schoolDao.getAllSchools()

    fun getSchoolByName(schoolName: String): String? =
       schoolDao.getSchoolByName(schoolName)

    fun getSchoolTeacherByNameSchool(schoolName: String, teacherId: String): String? =
        schoolDao.getSchoolTeacherByName(schoolName,teacherId)

    fun addTeacher(schoolId: String, teacherName: String) =
        schoolDao.addTeacher(schoolId,teacherName)

    fun removeTeacher(schoolId: String, teacherId: List<String>) =
        schoolDao.removeTeacherFromSchool(teacherId,schoolId)

    fun getTeachers(schoolId: String,searchKey: String?) =
        schoolDao.getTeachers(schoolId,searchKey)

    fun addStudent(schoolId: String, studentName: String) =
        schoolDao.addStudent(schoolId,studentName)

    fun removeStudent(schoolId: String, studentId: List<String>) =
        schoolDao.removeStudentFromSchool(studentId,schoolId)

    fun getStudents(schoolId: String,searchKey: String?) =
        schoolDao.getStudents(schoolId,searchKey)


}