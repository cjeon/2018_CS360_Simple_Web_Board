package com.example.dbprj.DbInterfaces

import com.example.dbprj.entities.Comment

interface CommentService {
    fun createComment(comment: Comment): Comment
    fun findByPostId(postId: Long): List<Comment>
}