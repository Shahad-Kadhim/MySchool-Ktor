package com.example.util

import com.example.authentication.Role
import com.example.models.DutyDto
import com.example.dao.toRole
import com.example.database.PostType
import com.example.database.entities.*
import com.example.database.entities.Notifications
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
    firebaseToken = this[Users.firebaseToken],
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
    phone = this[Users.phone],
    firebaseToken = this[Users.firebaseToken],
)

fun ResultRow.toSchool() = School(
    id=this[Schools.id],
    name = this[Schools.name],
    mangerId = this[Schools.mangerId]
)

fun ResultRow.toDutySubmit(studentName: String) =
    DutySubmit(
        studentId = this[DutyStudent.studentId],
        dutyId = this[DutyStudent.dutyId],
        submitDate = this[DutyStudent.submitDate],
        solutionLink = this[DutyStudent.solutionLink],
        studentName = studentName
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
        it[firebaseToken] = user.firebaseToken
    }
}

fun NotificationUser.addNotifications(notificationsId: String, users: List<String>){
    this.batchInsert(users){ item ->
        this[notificationId] = notificationsId
        this[userId] = item
    }
}
fun Notifications.insertNotification(notification: Notification){
    this.insert{
        it[id] = notification.id
        it[title] = notification.title
        it[content] = notification.content
        it[date] = notification.date
    }
}

fun ResultRow.toUser()=
    User(
        this[Users.id],
        this[Users.name],
        this[Users.password],
        this[Users.phone],
        this[Users.role].toRole(),
        this[Users.firebaseToken]
    )

fun ResultRow.toPostDto(authorName: String)=
    PostDto(
        this[Posts.id],
        this[Posts.title],
        this[Posts.content],
        this[Posts.payload],
        authorName,
        this[Posts.datePosted],
        this[Posts.type].toPostType()
    )

fun ResultRow.toPostDetailsDto(authorName: String,comments: List<CommentDto>)=
    PostDetailsDto(
        this[Posts.id],
        this[Posts.title],
        this[Posts.content],
        this[Posts.payload],
        authorName,
        this[Posts.datePosted],
        this[Posts.type].toPostType(),
        comments
    )

fun ResultRow.toLessonDto(authorName: String)=
    LessonDto(
        this[Posts.id],
        this[Posts.title],
        this[Posts.content],
        this[Posts.payload],
        authorName,
        this[Posts.datePosted],
        this[Lesson.lastDateUpdated]
    )

fun ResultRow.toDutyDto(authorName: String)=
    DutyDto(
        this[Posts.id],
        this[Posts.title],
        this[Posts.content],
        this[Posts.payload],
        authorName,
        this[Posts.datePosted],
        this[Duties.degree]
    )

fun String.toPostType(): PostType =
    when(this){
        PostType.LESSON.name -> PostType.LESSON
        PostType.DUTY.name -> PostType.DUTY
        else -> PostType.DUTY
    }

fun ResultRow.toCommentDto(authorName: String?) =
    CommentDto(
        id =this[Comments.id],
        authorName = authorName ?: "NONE",
        content = this[Comments.content],
        postId = this[Comments.postId],
        dateCommented = this[Comments.dateCommented]
    )

fun ResultRow.toNotification(): Notification =
    Notification(
        id= this[Notifications.id],
        title= this[Notifications.title],
        content= this[Notifications.content],
        date= this[Notifications.date]
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


fun Posts.insertPost(post: Post){
    this.insert {
        it[id] = post.id
        it[title] = post.title
        it[content] = post.content
        it[classId] = post.classId
        it[payload] = post.payload
        it[datePosted] = post.datePosted
        it[authorId] = post.authorId
        it[type] = post.type.name
    }
}
fun DutyStudent.insertDutySolution(dutySubmit: DutySubmit){
    this.insert {
        it[studentId] =dutySubmit.studentId
        it[dutyId] =dutySubmit.dutyId
        it[submitDate] =dutySubmit.submitDate
        it[solutionLink] =dutySubmit.solutionLink
    }
}

fun Lesson.insertLesson(postId: String){
    this.insert{
        it[id] =postId
    }
}
fun Duties.insertDuty(postId: String){
    this.insert{
        it[id] =postId
    }
}



fun Comments.insertComment(comment: Comment){
    this.insert {
        it[id] = comment.id
        it[content] = comment.content
        it[postId] = comment.postId
        it[authorId] = comment.authorId
        it[dateCommented] = comment.dateCommented
    }
}
