package com.example.dbprj.DbImpls

import com.example.dbprj.DbInterfaces.CommentService
import com.example.dbprj.Repos.CommentRepository
import com.example.dbprj.entities.Comment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl @Autowired constructor(val repo: CommentRepository): CommentService {
    /**
     * todo
     * add createComment function at CommentService and implement it here.
     * see PostServiceImpl
     */

    /**
     * todo
     * add findByPostId function at CommentService and implement it here.
     * see UserServiceImpl
     */
}