package com.example.util

import com.example.database.entities.Classes
import com.example.database.entities.Students
import com.example.database.entities.Teachers
import com.example.models.Class
import com.example.models.Student
import com.example.models.Teacher
import com.mysql.cj.xdevapi.UpdateStatement
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import java.util.*

fun ResultRow.toStudent() = Student(
    id=this[Students.id],
    name = this[Students.name],
    password = this[Students.password],
    age = this[Students.age],
    note = this[Students.note],
    phone = this[Students.phone],
    stage = this[Students.stage]
)

fun ResultRow.toTeacher() = Teacher(
    id=this[Teachers.id],
    name = this[Teachers.name],
    password = this[Teachers.password],
    teachingSpecialization = this[Teachers.teachingSpecialization],
    phone = this[Teachers.phone],
)

fun ResultRow.toClass() = Class(
    id=this[Classes.id],
    name = this[Classes.name],
    teacherId = this[Classes.teacherId],
    schoolId = this[Classes.schoolId],
    stage = this[Classes.stage]
)

fun Classes.insertClass(nClass:Class){
    this.insert {
        it[id] = nClass.id
        it[name] = nClass.name
        it[teacherId]= nClass.teacherId
        it[stage]=nClass.stage
    }
}

fun Students.insertStudent(student: Student){
    this.insert {
        it[id] = UUID.randomUUID().toString()
        it[name] = student.name
        it[password]= student.password
        it[phone]= student.phone
        it[age]= student.age
        it[note]= student.note
        it[stage]=student.stage
    }
}


fun Teachers.insertTeacher(teacher: Teacher){
    this.insert {
        it[id] = UUID.randomUUID().toString()
        it[name] = teacher.name
        it[password]= teacher.password
        it[phone]= teacher.phone
        it[teachingSpecialization] = teacher.teachingSpecialization
    }
}
