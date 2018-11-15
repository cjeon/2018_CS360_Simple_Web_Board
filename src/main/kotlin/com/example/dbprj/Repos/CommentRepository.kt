package com.example.dbprj.Repos

import com.example.dbprj.entities.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository: JpaRepository<Comment, Long> {
    /**
     * todo
     * add findByPostId. see UserRepository
     */
}