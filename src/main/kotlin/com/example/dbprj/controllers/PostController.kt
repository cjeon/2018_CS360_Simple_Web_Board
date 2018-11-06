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
import org.springframework.web.bind.annotation.PostMapping


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
    fun postPost(@ModelAttribute postPayload: PostPayload): String {
        if (!postPayload.validatePayload(userServiceImpl)) {
            return "error"
        }
        postServiceImpl?.createPost(postPayload.toPostEntity())
        return "view"
    }
}

data class PostPayload(var userId: String? = null,
                       var password: String? = null,
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