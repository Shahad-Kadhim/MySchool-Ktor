package com.example.mappers

import com.example.Mapper
import com.example.database.entities.Students
import com.example.models.Student
import com.example.util.toStudent
import org.jetbrains.exposed.sql.selectAll

class StudentMapper: Mapper<Students,Student> {

    override fun map(input: Students): Student =
        Students.selectAll().map {
            it.toStudent()
        }.first()

}