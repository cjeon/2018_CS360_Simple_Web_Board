package com.example.dbprj.DbInterfaces

import com.example.dbprj.entities.Report

interface ReportService {
    fun createReport(report: Report): Report
    fun findReportByPostId(postId: Long): List<Report>
}