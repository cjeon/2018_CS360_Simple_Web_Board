package com.example.dbprj.entities

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.jetbrains.annotations.NotNull
import java.util.*
import javax.persistence.*

// table definition
@Entity
@Table(name = "Report")
data class Report(@Id
                  @GeneratedValue(strategy = GenerationType.AUTO)
                  var id: Long? = null,
                  @field:CreationTimestamp
                  @Temporal(TemporalType.TIMESTAMP)
                  @Column(name = "created_at", nullable = false)
                  var createdAt: Date? = null,
                  @NotNull
                  @Temporal(TemporalType.TIMESTAMP)
                  @field:UpdateTimestamp
                  @Column(name = "updated_at", nullable = false)
                  var updatedAt: Date? = null,
                  @NotNull
                  @ManyToOne
                  @JoinColumn(name = "post_id", nullable = false)
                  var post: Post? = null,
                  @NotNull
                  @ManyToOne
                  @JoinColumn(name = "user_id", nullable = false)
                  var user: User? = null
)
