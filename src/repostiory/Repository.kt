package com.example.repostiory

import com.example.database.Dao
import com.example.models.Student

object Repository {
    private val database = Dao()

    fun getAllStudent() =
        database.getAllStudents()

    fun addStudent(student: Student) =
        database.addStudent(student)

    fun deleteStudent(id:String) =
        database.removeStudent(id)

    fun getUserByNameAndPassword(name: String, password: String) =
       database.getStudentByNameAndPassword(name,password)?.let {
           Student(
               it.name,
               it.password,
               it.age,
               it.note,
               it.phone,
               it.stage
           )
       }

}