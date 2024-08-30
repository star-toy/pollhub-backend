// src/main/java/com/startoy/pollhub/domain/Poll.java
package com.startoy.pollhub.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ph_poll")
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poll_id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = true, length = 255)
    private String title;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 20, nullable = false)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;
}
