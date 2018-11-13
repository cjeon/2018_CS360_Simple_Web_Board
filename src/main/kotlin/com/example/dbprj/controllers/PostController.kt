package com.example.dbprj.controllers

import com.example.dbprj.DbImpls.PostServiceImpl
import com.example.dbprj.DbImpls.UserServiceImpl
import com.example.dbprj.entities.Post
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
class PostController {
    @Autowired
    @NonNull
    var postServiceImpl: PostServiceImpl? = null

    @Autowired
    @NonNull
    var userServiceImpl: UserServiceImpl? = null

    @GetMapping("/post")
    fun getPost(model: Model): String {
        model.addAttribute("post_payload", PostPayload())
        return "post"
    }

    @PostMapping("/post")
    fun createPost(model: Model, @ModelAttribute postPayload: PostPayload): RedirectView {
        if (!postPayload.validatePayload(userServiceImpl)) {
            return RedirectView("error")
        }
        model.addAttribute("title", postPayload.title)
        model.addAttribute("text", postPayload.text)
        val post = postServiceImpl?.createPost(postPayload.toPostEntity())
        return RedirectView("post/${post?.id ?: ""}")
    }

    @GetMapping("/post/{post_id}")
    fun readPost(model: Model, @PathVariable(value="post_id") post_id: String): String {
        val post = postServiceImpl?.repo?.findById(post_id.toLong())
        if (post?.isPresent == true) {
            model.addAttribute("title", post.get().title)
            model.addAttribute("text", post.get().text)
            return "view"
        }
        return "error"
    @GetMapping("update/{post_id}")
    fun updatePost(model: Model, @PathVariable(value="post_id") post_id: String): String {
        val post = postServiceImpl?.repo?.findById(post_id.toLong())
        if (post?.isPresent != true) {
            return "error"
        }
        val p = post.get()
        model.addAllAttributes(mapOf( "post_payload" to PostPayload("", "", p.id.toString(), p.title, p.text)))
        return "update"
    }

    @PostMapping("update/{post_id}")
    fun requestUpdatePost(model: Model, @ModelAttribute postPayload: PostPayload, @PathVariable(value="post_id") post_id: String): String {
        // check if post exists. if not, return error.
        val originalPost = postServiceImpl?.repo?.findById(post_id.toLong())?.get() ?: return "error"
        // check if id & password matches
        val user = originalPost.user ?: return "error"
        if (user.userId != postPayload.userId || user.password != postPayload.password) {
            return "error"
        }
        // if all test passes, save and return view
        originalPost.title = postPayload.title
        originalPost.text = postPayload.text
        val updatedPost = postServiceImpl?.repo?.save(originalPost) ?: return "error"
        model.addAllAttributes(mapOf("title" to updatedPost.title, "text" to updatedPost.text, "id" to updatedPost.id))
        return "view"
    }
}

data class PostPayload(var userId: String? = null,
                       var password: String? = null,
                       var id: String? = null,
                       var title: String? = null,
                       var text: String? = null) {
    /**
     * validates the payload.
     * 1. Payload should have valid id & password.
     * 2. Payload should have non-null title & text.
     *
     * @return true if payload is valid, false if payload is invalid.
     */
    fun validatePayload(userServiceImpl: UserServiceImpl?): Boolean {
        if (userServiceImpl == null) return false
        val finalUserId = userId
        val finalPassword = password
        if (finalUserId == null || finalPassword == null) return false
        val results = userServiceImpl.findByUserId(finalUserId)
        return results.firstOrNull { it.password == finalPassword } != null
    }

    fun toPostEntity() = Post(title=this.title, text=this.text)
}