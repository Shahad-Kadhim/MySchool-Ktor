package com.example.repostiory

import com.example.dao.PostDao
import com.example.models.Post

class PostRepository(
    private val postDao: PostDao
) {

    fun createPost(post: Post){
        postDao.createPost(post)
    }

    fun getPostsByClassId(classId: String) =
        postDao.getPostsByClassId(classId)

    fun getLessonByCLassId(classId: String) =
        postDao.getLessonByClassId(classId)

    fun getPostDetails(postId: String) =
        postDao.getPostByPostId(postId)

    fun getTeacherDuties(teacherId: String) =
        postDao.getTeacherDuties(teacherId)
}