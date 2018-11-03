package com.example.dbprj.DALs

import com.example.dbprj.entities.Post

interface PostDAL {
    fun fuzzySearchPost(query: String): List<Post>
    fun wildCardSearchPost(query: String): List<Post>
}