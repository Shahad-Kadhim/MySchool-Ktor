package com.example.util

import com.example.authentication.Role
import com.example.database.entities.*
import com.example.models.*
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

fun ResultRow.toTeacherList() = TeacherList(
    id=this[Teachers.id],
    name = this[Teachers.name],
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

fun ResultRow.toManger() = Manger(
    id=this[Mangers.id],
    name = this[Mangers.name],
    password = this[Mangers.password]
)

fun ResultRow.toSchool() = School(
    id=this[Schools.id],
    name = this[Schools.name],
    mangerId = this[Schools.mangerId]
)

fun Classes.insertClass(nClass:Class){
    this.insert {
        it[id] = nClass.id
        it[name] = nClass.name
        it[teacherId]= nClass.teacherId
        it[schoolId]= nClass.schoolId
        it[stage]=nClass.stage
    }
}

fun Students.insertStudent(student: Student){
    this.insert {
        it[id] = student.id
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
        it[id] = teacher.id
        it[name] = teacher.name
        it[password]= teacher.password
        it[phone]= teacher.phone
        it[teachingSpecialization] = teacher.teachingSpecialization
    }
}

fun Mangers.insertManger(manger: Manger){
    this.insert {
        it[id] = manger.id
        it[name] = manger.name
        it[password]= manger.password
    }
}

fun Schools.insertSchool(school: School){
    this.insert {
        it[id] = school.id
        it[name] = school.name
        it[mangerId]= school.mangerId
    }
}

fun TeachersSchool.joinTeacher(school: String, teacher: String){
    this.insert {
        it[teacherId] = teacher
        it[schoolId] = school
        it[dateJoined]= Date().time
    }
}


fun String.toRole(): Role?=
    when(this.lowercase()){
        "teacher" ->{
            Role.TEACHER
        }
        "student" ->{
            Role.STUDENT
        }
        "manger" ->{
            Role.MANGER
        }
        else ->{
            null
        }
    }