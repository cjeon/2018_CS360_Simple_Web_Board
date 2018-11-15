package com.example.dbprj.DbInterfaces

import com.example.dbprj.entities.Comment

interface CommentService {
    /**
     * todo
     * add createComment function
     * see PostService
     */
    fun createComment(comment: Comment): Comment

    /**
     * todo
     * add any function that can be used to find comments that belong to a post.
     */
    fun findByPostId(postId: Long): List<Comment>
}