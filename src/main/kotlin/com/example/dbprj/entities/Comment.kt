package com.example.dbprj.entities

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.jetbrains.annotations.NotNull
import java.util.*
import javax.persistence.*

// table definition
@Entity
@Table(name = "Comment")
data class Comment(@Id
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
                var updatedAt: Date? = null
                /*
                * todo ADD text, user, post column
                * see Post.kt for help
                * column names should be `text`, `post_id`, `user_id`
                * */
)