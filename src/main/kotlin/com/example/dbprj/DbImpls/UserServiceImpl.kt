package com.example.dbprj.DbImpls

import com.example.dbprj.DbInterfaces.UserService
import com.example.dbprj.Repos.UserRepository
import com.example.dbprj.entities.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl @Autowired constructor(val repo: UserRepository): UserService {
    override fun createUser(user: User): User = repo.save(user)

    override fun findByUserId(userId: String) : List<User> = repo.findByUserId(userId)

    fun validateUser(userId : String, password : String) : Boolean {
        val list : List<User> = findByUserId(userId)
        if(list.isEmpty()) return false
        val foundUser : User = list.first()
        if(foundUser.password == password) return true
        return false
    }
}