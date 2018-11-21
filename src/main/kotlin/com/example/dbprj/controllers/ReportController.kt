package com.example.dbprj.controllers

import com.example.dbprj.DbImpls.PostServiceImpl
import com.example.dbprj.DbImpls.UserServiceImpl
import com.example.dbprj.DbImpls.ReportServiceImpl
import com.example.dbprj.entities.Report
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
class ReportController {
    @Autowired
    @NonNull
    var postServiceImpl: PostServiceImpl? = null

    @Autowired
    @NonNull
    var userServiceImpl: UserServiceImpl? = null

    @Autowired
    @NonNull
    var reportServiceImpl: ReportServiceImpl? = null

    @GetMapping("report/{post_id}")
    fun getReport(model: Model, @PathVariable(value="post_id") postId: String): String {
        model.addAttribute("report_payload", ReportPayload(postId = postId))
        return "report"
    }

    @PostMapping("report/{post_id}")
    fun requestGetReport(model: Model, @ModelAttribute reportPayload: ReportPayload, @PathVariable(value="post_id") post_id: String): RedirectView {
        if(userServiceImpl?.validateUser(reportPayload.userId!!, reportPayload.password!!) != true) return RedirectView("/error")
        val currentReports = reportServiceImpl?.repo?.findReportByPostId(post_id.toLong())!!
        if(currentReports.isEmpty()){
            model.addAttribute("post_payload", ReportPayload(postId = post_id))
            val report = Report(
                    post = postServiceImpl?.repo?.findById(post_id.toLong())?.get(),
                    user = userServiceImpl?.repo?.findByUserId(reportPayload.userId!!)?.first())
            reportServiceImpl?.repo?.save(report)
            return RedirectView("/post/$post_id")
        }
        for(item in currentReports){
            var currentID = item.user?.userId
            if(currentID == reportPayload.userId) return RedirectView("/error")
        }
        model.addAttribute("post_payload", ReportPayload(postId = post_id))
        val report = Report(
                post = postServiceImpl?.repo?.findById(post_id.toLong())?.get(),
                user = userServiceImpl?.repo?.findByUserId(reportPayload.userId!!)?.first())
        reportServiceImpl?.repo?.save(report)
        return RedirectView("/post/$post_id")
    }
}

data class ReportPayload(var userId: String? = null,
                          var password: String? =null,
                          var postId: String? = null)