package com.example.dbprj.Repos

import com.example.dbprj.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByUserId(userId: String): List<User>

    @Query("SELECT CASE WHEN COUNT(u) > 0 then true else false end FROM User u WHERE u.userId=?1 AND u.password=?2")
    fun isValidUser(userId: String, password: String): Boolean
}