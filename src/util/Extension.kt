package com.example.util

import com.example.authentication.Role
import com.example.dao.toRole
import com.example.database.entities.*
import com.example.models.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insert
import java.util.*

fun ResultRow.toStudent() = StudentDto(
    id=this[Students.id],
    name = this[Users.name],
    age = this[Students.age],
    note = this[Students.note],
    phone = this[Users.phone],
    stage = this[Students.stage]
)

fun ResultRow.toUserDto() = UserDto(
    id=this[Users.id],
    name = this[Users.name],
    phone = this[Users.phone],
    role = this[Users.role].toRole()
)

fun ResultRow.toTeacher() = Teacher(
    id=this[Teachers.id],
    name = this[Users.name],
    password = this[Users.password],
    teachingSpecialization = this[Teachers.teachingSpecialization],
    phone = this[Users.phone],
)

fun ResultRow.toTeacherList() = TeacherList(
    id=this[Teachers.id],
    name = this[Users.name],
    teachingSpecialization = this[Teachers.teachingSpecialization],
    phone =  this[Users.phone],
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
    name = this[Users.name],
    password = this[Users.password],
    phone = this[Users.phone]
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
        it[age]= student.age
        it[note]= student.note
        it[stage]=student.stage
    }
}


fun Teachers.insertTeacher(teacher: Teacher){
    this.insert {
        it[id] = teacher.id
        it[teachingSpecialization] = teacher.teachingSpecialization
    }
}

fun Mangers.insertManger(manger: Manger){
    this.insert {
        it[id] = manger.id
    }
}

fun Schools.insertSchool(school: School){
    this.insert {
        it[id] = school.id
        it[name] = school.name
        it[mangerId]= school.mangerId
    }
}

fun TeachersSchool.joinTeacher(school: String, teacher: TeacherList){
    this.insert {
        it[teacherId] = teacher.id
        it[schoolId] = school
        it[dateJoined]= Date().time
    }
}

fun StudentsSchool.joinStudent(schoolId: String, student: StudentDto){
    this.insert {
        it[studentId] = student.id
        it[this.schoolId] = schoolId
        it[dateJoined]= Date().time
    }
}


fun MemberClass.joinStudent(studentsId: List<String>, mClassId: String) =
    this.batchInsert(studentsId){ item  ->
        this[studentId] = item
        this[classId] = mClassId
        this[dateJoined] =Date().time
    }



fun Users.insertUser(user: User){
    this.insert {
        it[id] = user.id
        it[name] = user.name
        it[password] = user.password
        it[phone] = user.phone
        it[role] = user.role.name
    }
}
fun ResultRow.toUser()=
    User(
        this[Users.id],
        this[Users.name],
        this[Users.password],
        this[Users.phone],
        this[Users.role].toRole(),
    )

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

fun Any?.isNotNull() = this != null