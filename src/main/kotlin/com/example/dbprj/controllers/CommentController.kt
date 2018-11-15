package com.example.dbprj.controllers

import com.example.dbprj.DbImpls.CommentServiceImpl
import com.example.dbprj.DbImpls.PostServiceImpl
import com.example.dbprj.DbImpls.UserServiceImpl
import com.example.dbprj.entities.Comment
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
class CommentController {
    @Autowired
    @NonNull
    var postServiceImpl: PostServiceImpl? = null

    @Autowired
    @NonNull
    var userServiceImpl: UserServiceImpl? = null

    @Autowired
    @NonNull
    var commentServiceImpl: CommentServiceImpl? = null

    // todo get mapping for comment/post_id (ex: comment/1, comment/2)
    // write function "leaveComment"
    // create CommentPayload first and use that here.
    // and show comment.html.
    // comment.html should contain id so that server nor client remembers the user.
    @GetMapping("comment/{post_id}")
    fun leaveComment(model: Model, @PathVariable(value="post_id") postId: String): String {
        model.addAttribute("comment_payload", CommentPayload())
        return "comment"
    }

    // todo write "requestLeaveComment" function
    // this function
    // 1. creates Comment entity from commentPayload and post_id
    // 2. save comment entity
    // 3. redirect user to /post/post_id
    @PostMapping("comment/{post_id}")
    fun requestLeaveComment(model: Model,
                            @ModelAttribute commentPayload: CommentPayload,
                            @PathVariable(value="post_id") post_id: String): RedirectView {
        val comment = Comment(
                text = commentPayload.text,
                post = postServiceImpl?.repo?.findById(post_id.toLong())?.get(),
                user = userServiceImpl?.repo?.findByUserId(commentPayload.userId!!)?.first())
        commentServiceImpl?.repo?.save(comment)
        return RedirectView("/post/$post_id")
    }
}

// todo complete comment payload. see PostPayload.
data class CommentPayload(var userId: String? = null,
                          var password: String? = null,
                          var text: String? = null)