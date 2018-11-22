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

    private fun getQuery(id: Long) = "select * from `user_id_pw` where id = '$id'"

    @Autowired
    @NonNull
    var userServiceImpl: UserServiceImpl? = null

    override fun createPost(post: Post): Post {
        repo.save(post)
        return post
    }

    /**
     * validate 되었으면 Post 를 return. else, null.
     */
    fun validateUser(postId: String, userId: String?, userPassword: String?): Post? {
        if (userId == null || userPassword == null) return null;
        val postOptional = repo.findById(postId.toLong())
        if (!postOptional.isPresent) return null
        val post = postOptional.get()
        val user = post.user ?: return null
        // 억지로 native query 를 활용해서 view 를 사용한다.
        val userFromView = jdbcTemplate!!.queryForList(getQuery(user.id!!)).first()
        // 글의 작성자이면
        if (userFromView["user_id"] == userId && userFromView["password"] == userPassword) {
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