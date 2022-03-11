package com.example.dao

import com.example.database.PostType
import com.example.database.entities.Duties
import com.example.database.entities.Lesson
import com.example.database.entities.Posts
import com.example.models.LessonDto
import com.example.models.Post
import com.example.models.PostDto
import com.example.util.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class PostDao(
    private val teacherDao: TeacherDao
) {

    fun createPost(post: Post){
        transaction {
            Posts.insertPost(post)
        }
        insertByType(post.id,post.type)
    }

    private fun insertByType(id: String, type: PostType) {
        transaction {
            when(type){
                PostType.LESSON -> Lesson.insertLesson(id)
                PostType.DUTY -> Duties.insertDuty(id)
            }
        }
    }

    fun getPostsByClassId(classId: String): List<PostDto> =
        transaction {
            Posts.select(Posts.classId.eq(classId))
                .map {
                    it.toPostDto(teacherDao.getTeacherById(it[Posts.authorId])?.name ?: "")
                }
        }

    fun getLessonByClassId(classId: String): List<LessonDto> =
        transaction {
            (Lesson innerJoin Posts)
                .select(Posts.classId.eq(classId) and Posts.type.eq(PostType.LESSON.name))
                .map {
                    it.toLessonDto(teacherDao.getTeacherById(it[Posts.authorId])?.name ?: "")
                }
        }


}