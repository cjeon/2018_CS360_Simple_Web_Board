package com.example.dbprj.DbImpls

import com.example.dbprj.DbInterfaces.PostService
import com.example.dbprj.Repos.PostRepository
import com.example.dbprj.entities.Post
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PostServiceImpl @Autowired constructor(val repo: PostRepository) : PostService {

    override fun createPost(post: Post): Post {
        repo.save(post)
        return post
    }

    /**
     * validate 되었으면 Post 를 return. else, null.
     */
    fun validateUser(postId: String, userId: String?, userPassword: String?): Post? {
        val postOptional = repo.findById(postId.toLong())
        if (!postOptional.isPresent) return null
        val post = postOptional.get()
        val user = post.user ?: return null
        // 글의 작성자이면
        if (user.id.toString() == userId && user.password == userPassword) {
            return post
        }
        // 유저가 어드민이면
        if (user.isAdmin == true) {
            return post
        }
        // 해당이 없으면
        return null
    }
}