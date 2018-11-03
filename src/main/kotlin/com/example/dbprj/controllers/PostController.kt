package com.example.dbprj.controllers

import com.example.dbprj.DbImpls.PostServiceImpl
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
    @NonNull var postServiceImpl: PostServiceImpl? = null

    @GetMapping("/post")
    fun greetingForm(model: Model): String {
        model.addAttribute("post", Post())
        return "post"
    }

    @PostMapping("/post")
    fun greetingSubmit(@ModelAttribute post: Post): String {
        if (post.title == null || post.text == null) {
            return ""
        }
        postServiceImpl?.createPost(post)
        return "view"
    }
}