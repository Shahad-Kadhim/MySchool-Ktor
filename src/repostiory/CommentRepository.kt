package com.example.repostiory

import com.example.dao.CommentDao
import com.example.models.Comment

class CommentRepository(
    private val commentDao: CommentDao
) {

    fun createComment(comment: Comment){
        commentDao.createComment(comment)
    }

    fun getCommentsByPostId(classId: String) =
        commentDao.getCommentsInPost(classId)

}