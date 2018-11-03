package com.example.dbprj.DbInterfaces

import com.example.dbprj.entities.Post

interface PostService {
    fun createPost(post: Post): Post
}