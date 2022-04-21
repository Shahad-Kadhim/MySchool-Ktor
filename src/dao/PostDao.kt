package com.example.dao

import com.example.database.PostType
import com.example.database.entities.Duties
import com.example.database.entities.Lesson
import com.example.database.entities.Posts
import com.example.models.*
import com.example.util.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class PostDao(
    private val userDao: UserDao,
    private val commentDao: CommentDao,
    private val classDao: ClassDao,
    private val notificationDao: NotificationDao
) {

    fun createPost(post: Post){
        transaction {
            Posts.insertPost(post)
            insertByType(post.id,post.type)
            createNotificationToPublishPost(
                classDao.getStudentInClass(post.classId).map { it.id },
                post
            )

        }
    }


    private fun createNotificationToPublishPost(users: List<String>,post: Post){
        notificationDao.createNotification(
            Notification(
                id = UUID.randomUUID().toString(),
                title = "New Post in ${classDao.getClassById(post.classId)?.name} class",
                content = "${userDao.findUserById(post.authorId)} publish new Post ",
                date = Date().time
            ),
            users
        )
    }

    private fun insertByType(id: String, type: PostType) {
        when(type){
            PostType.LESSON -> Lesson.insertLesson(id)
            PostType.DUTY -> Duties.insertDuty(id)
        }
    }

    fun getPostsByClassId(classId: String): List<PostDto> =
        transaction {
            Posts.select(Posts.classId.eq(classId))
                .map {
                    it.toPostDto(userDao.findUserById(it[Posts.authorId])?.name ?: "")
                }
        }

    fun getLessonByClassId(classId: String): List<LessonDto> =
        transaction {
            (Lesson innerJoin Posts)
                .select(Posts.classId.eq(classId) and Posts.type.eq(PostType.LESSON.name))
                .map {
                    it.toLessonDto(userDao.findUserById(it[Posts.authorId])?.name ?: "")
                }
        }

    fun getPostByPostId(postId: String) =
        transaction {
            Posts.select(Posts.id.eq(postId))
                .map {
                    it.toPostDetailsDto(
                        userDao.findUserById(it[Posts.authorId])?.name ?: "",
                        commentDao.getCommentsInPost(postId)
                        )
                }.firstOrNull()
        }

    fun getTeacherDuties(teacherId: String) =
        transaction {
            (Duties innerJoin Posts)
                .select(Posts.authorId.eq(teacherId) and Posts.type.eq(PostType.DUTY.name))
                .map {
                    it.toDutyDto(userDao.findUserById(teacherId)?.name ?: "")
                }
        }

    fun getPostAuthor(postId: String) =
        transaction {
        }
}