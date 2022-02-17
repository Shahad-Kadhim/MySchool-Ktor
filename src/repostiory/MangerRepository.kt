package com.example.repostiory

import com.example.dao.MangerDao
import com.example.dao.User
import com.example.models.Manger
import com.example.route.userDao

class MangerRepository(
    private val mangerDao: MangerDao
) {

    fun getMangerByNameAndPassword(name: String, password: String): User? =
        userDao.findUserByNameAndPassword(name,password)


    fun getMangerSchools(id: String)=
        mangerDao.getSchools(id)

    fun getAllManger(): List<Manger> =
        mangerDao.getAllMangers()

    fun getMangerClasses(id: String)=
        mangerDao.getClasses(id)


}