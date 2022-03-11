package com.example.dao

import com.example.database.entities.Comments
import com.example.models.Comment
import com.example.models.CommentDto
import com.example.util.insertComment
import com.example.util.toCommentDto
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class CommentDao(
    private val userDao: UserDao
) {
    fun createComment(comment: Comment){
        transaction {
            Comments.insertComment(comment)
        }
    }

    fun getCommentsInPost(postId: String): List<CommentDto> =
        transaction {
            Comments.select(Comments.postId.eq(postId))
                .map{
                    it.toCommentDto(
                        authorName = userDao.findUserById(it[Comments.authorId])?.name
                    )
                }
        }


}