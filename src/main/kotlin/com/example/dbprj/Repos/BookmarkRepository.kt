package com.example.dbprj.Repos

import com.example.dbprj.entities.Bookmark
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BookmarkRepository: JpaRepository<Bookmark, Long> {
    @Query("select p.title, p.id, temp.user_id from post p join ( select post_id, u.user_id from user u join bookmark b on u.id = b.user_id WHERE b.user_id = :uid ) temp on p.id = temp.post_id",
            nativeQuery = true)
    fun findBookmarkByUserId(@Param("uid") userId: Long): List<Any>
    fun findBookmarkByPostId(PostId: Long): List<Bookmark>
}