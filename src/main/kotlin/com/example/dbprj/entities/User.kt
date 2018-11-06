package com.example.dbprj.entities

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.jetbrains.annotations.NotNull
import java.util.*
import javax.persistence.*

// table definition
@Entity
@Table(name = "User")
data class User(@Id
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
                @Column(nullable = false)
                var userId: String? = null,
                @NotNull
                @Column(nullable = false)
                var password: String? = null,
                @NotNull
                @Column(nullable = false)
                var isAdmin: Boolean? = false
)
