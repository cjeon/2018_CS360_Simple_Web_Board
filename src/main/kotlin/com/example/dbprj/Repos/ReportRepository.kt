package com.example.dbprj.Repos

import com.example.dbprj.entities.Report
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReportRepository: JpaRepository<Report, Long> {
    fun findReportByPostId(PostId: Long): List<Report>
}