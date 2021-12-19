package com.example.mappers

import com.example.Mapper
import com.example.database.entities.DBStudentEntity
import com.example.models.Student

class StudentMapper: Mapper<DBStudentEntity,Student> {

    override fun map(input: DBStudentEntity): Student =
        Student(
            id = input.id,
            name =input.name,
            password = input.password,
            phone = input.phone,
            age = input.age,
            stage = input.stage,
            note = input.note
        )


}