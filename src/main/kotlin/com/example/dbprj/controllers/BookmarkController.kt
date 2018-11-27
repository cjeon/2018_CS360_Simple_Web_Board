package com.example.dbprj.controllers

import com.example.dbprj.DbImpls.BookmarkServiceImpl
import com.example.dbprj.DbImpls.PostServiceImpl
import com.example.dbprj.DbImpls.UserServiceImpl
import com.example.dbprj.entities.Bookmark
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.lang.NonNull
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.view.RedirectView

@Controller
class BookmarkController {
    @Autowired
    @NonNull
    var postServiceImpl: PostServiceImpl? = null

    @Autowired
    @NonNull
    var userServiceImpl: UserServiceImpl? = null

    @Autowired
    @NonNull
    var bookmarkServiceImpl: BookmarkServiceImpl? = null

    @GetMapping("bookmark/{post_id}")
    fun getBookmark(model: Model, @PathVariable(value="post_id") postId: String): String {
        model.addAttribute("bookmark_payload", BookmarkPayload(postId = postId))
        return "bookmark"
    }

    @PostMapping("bookmark/{post_id}")
    fun requestGetBookmark(model: Model, @ModelAttribute bookmarkPayload: BookmarkPayload, @PathVariable(value="post_id") post_id: String): RedirectView {
        if(userServiceImpl?.validateUser(bookmarkPayload.userId!!, bookmarkPayload.password!!) != true) return RedirectView("/error")
        val currentBookmarks = bookmarkServiceImpl?.repo?.findBookmarkByPostId(post_id.toLong())!!
        if(currentBookmarks.isEmpty()){
            model.addAttribute("post_payload", BookmarkPayload(postId = post_id))
            val bookmark = Bookmark(
                    post = postServiceImpl?.repo?.findById(post_id.toLong())?.get(),
                    user = userServiceImpl?.repo?.findByUserId(bookmarkPayload.userId!!)?.first())
            bookmarkServiceImpl?.repo?.save(bookmark)
            return RedirectView("/post/$post_id")
        }
        for(item in currentBookmarks){
            var currentID = item.user?.userId
            bookmarkServiceImpl?.repo?.delete(item)
            if(currentID == bookmarkPayload.userId) return RedirectView("/post/$post_id")
        }
        model.addAttribute("post_payload", BookmarkPayload(postId = post_id))
        val bookmark = Bookmark(
                post = postServiceImpl?.repo?.findById(post_id.toLong())?.get(),
                user = userServiceImpl?.repo?.findByUserId(bookmarkPayload.userId!!)?.first())
        bookmarkServiceImpl?.repo?.save(bookmark)
        return RedirectView("/post/$post_id")
    }

    @GetMapping("bookmark2")
    fun getBookmark(model: Model): String {
        model.addAttribute("post_payload", PostPayload())
        return "bookmark2"
    }

    @PostMapping("bookmark2")
    fun goBookmark(model: Model, @ModelAttribute postPayload: PostPayload): RedirectView {
        if (!postPayload.validatePayload(userServiceImpl)) {
            return RedirectView("error")
        }
        val id = postPayload.userId
        model.addAllAttributes(mapOf("id" to id))
        return RedirectView("/bookmark_list/${id ?: ""}")
    }

    @GetMapping("bookmark_list/{post_id}")
    fun listBookmarks(model: Model, @PathVariable(value="post_id") userId: String): String {
        val user = userServiceImpl?.findByUserId(userId)?.first()!!
        val bookmarks = bookmarkServiceImpl?.findBookmarkByUserId(user.id!!)!!
        val list = bookmarks.map { it.post!! }
        model.addAttribute("posts", list)
        return "bookmark_list"
    }
}

data class BookmarkPayload(var userId: String? = null,
                          var password: String? =null,
                          var postId: String? = null)