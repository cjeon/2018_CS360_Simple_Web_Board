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

}