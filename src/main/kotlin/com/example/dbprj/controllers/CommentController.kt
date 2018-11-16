package com.example.dbprj.controllers

import com.example.dbprj.DbImpls.CommentServiceImpl
import com.example.dbprj.DbImpls.PostServiceImpl
import com.example.dbprj.DbImpls.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.lang.NonNull
import org.springframework.stereotype.Controller

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

    // todo write "requestLeaveComment" function
    // this function
    // 1. creates Comment entity from commentPayload and post_id
    // 2. save comment entity
    // 3. redirect user to /post/post_id
}

// todo complete comment payload. see PostPayload.
data class CommentPayload(var userId: String? = null)