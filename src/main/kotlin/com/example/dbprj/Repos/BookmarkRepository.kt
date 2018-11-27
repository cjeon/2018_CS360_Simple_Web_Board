package com.example.dbprj.Repos

import com.example.dbprj.entities.Bookmark
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookmarkRepository: JpaRepository<Bookmark, Long> {
    fun findBookmarkByUserId(UserId: Long): List<Bookmark>
    fun findBookmarkByPostId(PostId: Long): List<Bookmark>
}