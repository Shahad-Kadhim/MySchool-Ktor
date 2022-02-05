package com.example.repostiory

import com.example.dao.ClassDao
import com.example.models.Class

class ClassRepository(
    private val  classDao: ClassDao
) {



    fun getClassById(classId: String)=
        classDao.getClassById(classId)

    fun addClass(mClass: Class) =
        classDao.createClass(mClass)


    fun getMembers(classId: String) =
        classDao.getMembersOfClass(classId)

    fun getAllClasses(): List<Class> =
        classDao.getAllClasses()


}