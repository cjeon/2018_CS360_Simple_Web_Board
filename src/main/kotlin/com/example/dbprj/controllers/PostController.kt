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
        val post = postServiceImpl?.createPost(postPayload.toPostEntity()) ?: return RedirectView("error")
        model.addAllAttributes(mapOf("title" to post.title, "text" to post.text, "id" to post.id))
        return RedirectView("post/${post.id ?: ""}")
    }

    @GetMapping("/post/{post_id}")
    fun readPost(model: Model, @PathVariable(value="post_id") post_id: String): String {
        val post = postServiceImpl?.repo?.findById(post_id.toLong())
        if (post?.isPresent != true) {
            return "error"
        }
        val p = post.get()
        model.addAllAttributes(mapOf("title" to p.title, "text" to p.text, "id" to p.id))
        return "view"
    }

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
        val post = postServiceImpl?.validateUser(post_id, postPayload.userId, postPayload.password) ?: return "error"
        post.title = postPayload.title
        post.text = postPayload.text
        val updatedPost = postServiceImpl?.repo?.save(post) ?: return "error"
        model.addAllAttributes(mapOf("title" to updatedPost.title, "text" to updatedPost.text, "id" to updatedPost.id))
        return "view"
    }

    @GetMapping("delete/{post_id}")
    fun deletePost(model: Model, @PathVariable(value="post_id") post_id: String): String {
        model.addAttribute("post_payload", PostPayload(id=post_id))
        return "delete_validate"
    }

    @PostMapping("delete/{post_id}")
    fun requestDeletePost(model: Model, @ModelAttribute postPayload: PostPayload, @PathVariable(value="post_id") post_id: String): RedirectView {
        model.addAttribute("post_payload", PostPayload(id=post_id))
        val post = postServiceImpl?.validateUser(post_id, postPayload.userId, postPayload.password) ?: return RedirectView("error")
        postServiceImpl?.repo?.delete(post)
        return RedirectView("/")
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