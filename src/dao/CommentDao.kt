package com.example.dao

import com.example.database.entities.Comments
import com.example.database.entities.Posts
import com.example.models.Comment
import com.example.models.CommentDto
import com.example.models.Notification
import com.example.models.Post
import com.example.util.insertComment
import com.example.util.toCommentDto
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class CommentDao(
    private val userDao: UserDao,
    private val notificationDao: NotificationDao,
) {
    fun createComment(comment: Comment){
        transaction {
            Comments.insertComment(comment)
            Posts.select(Posts.id.eq(comment.postId)).map {
                userDao.findUserById(it[Posts.authorId])
            }.firstOrNull()?.let {
                getPostAuthor(comment.postId)?.takeIf {
                    it != comment.authorId
                }?.let { postAuthorId ->
                    createNotificationToCommentInPost(postAuthorId,comment)
                }
            }
        }
    }


    private fun createNotificationToCommentInPost(postAuthorId: String,comment: Comment){
        notificationDao.createNotification(
            Notification(
                id = UUID.randomUUID().toString(),
                title = "${userDao.findUserById(comment.authorId)?.name} comment on Your Post",
                content = comment.content,
                date = Date().time
            ),
            listOf(postAuthorId)
        )
    }


    private fun  getPostAuthor(postId: String) =
        transaction {
            Posts.select(Posts.id.eq(postId)).map { it[Posts.authorId] }.firstOrNull()
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