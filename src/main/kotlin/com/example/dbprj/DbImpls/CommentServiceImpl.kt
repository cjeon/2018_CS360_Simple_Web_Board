package com.example.dbprj.DbImpls

import com.example.dbprj.DbInterfaces.CommentService
import com.example.dbprj.Repos.CommentRepository
import com.example.dbprj.entities.Comment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl @Autowired constructor(val repo: CommentRepository): CommentService {
    override fun createComment(comment: Comment): Comment {
        repo.save(comment)
        return comment
    }
    override fun findByPostId(postId: Long) : List<Comment> = repo.findByPostId(postId)
}