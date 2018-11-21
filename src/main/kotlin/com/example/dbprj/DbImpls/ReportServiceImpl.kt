package com.example.dbprj.DbImpls

import com.example.dbprj.DbInterfaces.ReportService
import com.example.dbprj.Repos.ReportRepository
import com.example.dbprj.entities.Report
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ReportServiceImpl @Autowired constructor(val repo: ReportRepository): ReportService {
    override fun createReport(report: Report): Report {
        repo.save(report)
        return report
    }
    override fun findReportByPostId(postId: Long) : List<Report> = repo.findReportByPostId(postId)
}