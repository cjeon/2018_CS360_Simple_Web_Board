package com.example.dbprj.DbImpls

import com.example.dbprj.DbInterfaces.BookmarkService
import com.example.dbprj.Repos.BookmarkRepository
import com.example.dbprj.entities.Bookmark
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookmarkServiceImpl @Autowired constructor(val repo: BookmarkRepository): BookmarkService {
    override fun createBookmark(bookmark: Bookmark): Bookmark {
        repo.save(bookmark)
        return bookmark
    }
    override fun findBookmarkByUserId(userId: Long) : List<Any> = repo.findBookmarkByUserId(userId)
    override fun findBookmarkByPostId(postId: Long) : List<Bookmark> = repo.findBookmarkByPostId(postId)
}