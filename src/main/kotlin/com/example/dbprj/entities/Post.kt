package com.example.dbprj.entities

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.jetbrains.annotations.NotNull
import java.util.*
import javax.persistence.*

// table definition
@Entity
@Table(name = "Post")
data class Post(@Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
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
                @Column(nullable = false)
                var title: String? = null,
                @NotNull
                @Column(nullable = false)
                var text: String? = null)