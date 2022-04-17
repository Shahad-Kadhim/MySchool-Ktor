package com.example.dao

import com.example.authentication.Role
import com.example.database.PostType
import com.example.database.entities.*
import com.example.models.ClassDto
import com.example.models.SchoolDto
import com.example.models.StudentDto
import com.example.models.UserDto
import com.example.util.toClass
import com.example.util.toDutyDto
import com.example.util.toStudent
import com.example.util.toUserDto
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class StudentDao(
    private val teacherDao: TeacherDao,
    private val userDao: UserDao
) {

    fun getAllStudents(): List<StudentDto> =
        transaction {
            (Students innerJoin Users)
                .select(Users.role.eq(Role.STUDENT.name))
                .map {
                it.toStudent()
            }
        }

    fun getAllStudents(studentsId: List<String>, searchKey: String?): List<UserDto> =
        transaction {
            (Students innerJoin Users)
                .select(Users.role.eq(Role.STUDENT.name) and Users.id.inList(studentsId) and Users.name.like("${searchKey ?: ""}%") )
                .map {
                    it.toUserDto()
                }
        }



    fun getStudentById(id: String): StudentDto? =
        transaction {
            (Users innerJoin Students)
                .select(Students.id.eq(id)).firstOrNull()?.toStudent()
        }

    fun getStudentByName(name: String): StudentDto? =
        transaction {
            (Users innerJoin Students)
                .select(Users.name.eq(name)).firstOrNull()?.toStudent()
        }

    private fun getListOfClasses(studentId: String) =
        transaction {
            MemberClass
                .select{(MemberClass.studentId.eq(studentId))}
                .map {
                    it[MemberClass.classId]
                }
        }

    fun  getStudentClasses(studentId: String,searchKey: String?) =
        transaction{
            Classes.select(
                Classes.id.inList(getListOfClasses(studentId)) and Classes.name.like("${searchKey ?: ""}%")
            ).map {
                ClassDto(
                    it[Classes.id],
                    it[Classes.name],
                    teacherDao.getTeacherById(it[Classes.teacherId])?.name ?: "",
                    it[Classes.stage]
                )
            }
        }
    //TODO LATER CHANGE RESPONSE
    fun getSchools(studentId: String) =
       transaction {
            StudentsSchool
                .select { (StudentsSchool.studentId.eq(studentId)) }
                .map {
                    SchoolDto(
                        it[StudentsSchool.schoolId],
                        "",
                        ""
                    )
                }
        }

    fun getAssignmentForStudent(studentId: String) =
        transaction{
            (Duties innerJoin Posts)
                .select(Posts.classId.inList(getListOfClasses(studentId)) and Posts.type.eq(PostType.DUTY.name))
                .map {
                    it.toDutyDto(userDao.findUserById(it[Posts.authorId])?.name ?: "")
                }
        }
    }
