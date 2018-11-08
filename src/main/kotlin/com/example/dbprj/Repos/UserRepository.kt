package com.example.dbprj.Repos

import com.example.dbprj.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByUserId(userId: String): List<User>
}