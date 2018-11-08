package com.example.dbprj.DbInterfaces

import com.example.dbprj.entities.User

interface UserService {
    fun createUser(user: User): User

    fun findByUserId(userId: String) : List<User>
}