package com.example.dbprj.DbInterfaces

import com.example.dbprj.entities.Bookmark

interface BookmarkService {
    fun createBookmark(bookmark: Bookmark): Bookmark
    fun findBookmarkByUserId(userId: Long): List<Bookmark>
    fun findBookmarkByPostId(postId: Long): List<Bookmark>
}