package com.example.dbprj.DbImpls

import com.example.dbprj.DbInterfaces.PostService
import com.example.dbprj.Repos.PostRepository
import com.example.dbprj.entities.Post
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.lang.NonNull
import org.springframework.stereotype.Service



@Service
class PostServiceImpl @Autowired constructor(val repo: PostRepository) : PostService {
    @Autowired
    @NonNull
    private val jdbcTemplate: JdbcTemplate? = null



    @Autowired
    @NonNull
    var userServiceImpl: UserServiceImpl? = null

    override fun createPost(post: Post): Post {
        repo.save(post)
        return post
    }

    private fun getQuery(id: Long) = "select * from `user_id_pw` where id = '$id'"

    /**
     * Used to validate user. If the user
     * 1. Is writer of the post or
     * 2. Has admin privilege
     * return post. else, return null.
     */
    fun validateUser(postId: String, userId: String?, userPassword: String?): Post? {
        if (userId == null || userPassword == null) return null;
        val postOptional = repo.findById(postId.toLong())
        if (!postOptional.isPresent) return null
        val post = postOptional.get()
        val author = post.user ?: return null
        // use view via native query.
        val authorInfo = jdbcTemplate!!.queryForList(getQuery(author.id!!)).first()
        // if is author of the post
        if (authorInfo["user_id"] == userId && authorInfo["password"] == userPassword) {
            return post
        }

        // if is admin
        val user = userServiceImpl?.findByUserId(userId)?.firstOrNull() ?: return null
        if (user.password != userPassword) return null
        if (user.isAdmin == true) {
            return post
        }
        // if satisfies nothing
        return null
    }
}