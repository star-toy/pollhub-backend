// src/main/java/com/startoy/pollhub/domain/BoardPost.java
package com.startoy.pollhub.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(name = "poll_id", nullable = false)
    private Long pollId;

    @Column(name = "file_id")
    private Long fileId;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 20, nullable = false)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;
}
