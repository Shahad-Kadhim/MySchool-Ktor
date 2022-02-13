package com.example.repostiory

import com.example.dao.MangerDao
import com.example.models.Manger

class MangerRepository(
    private val mangerDao: MangerDao
) {

    fun addManger(manger: Manger){
        mangerDao.createManger(manger)
    }

    fun getMangerById(id: String)=
        mangerDao.getMangerById(id)

    fun deleteManger(id: String) =
        mangerDao.removeManger(id)

    fun getMangerByNameAndPassword(name: String, password: String): Manger? =
        mangerDao.getMangerByNameAndPassword(name,password)


    fun getMangerSchools(id: String)=
        mangerDao.getSchools(id)

    fun getAllManger(): List<Manger> =
        mangerDao.getAllMangers()

    fun getMangerClasses(id: String)=
        mangerDao.getClasses(id)


}