package com.startoy.pollhub.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ph_option")
public class PollOption extends BaseEntities{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "poll_id", nullable = false)
    private Long pollId;

    @Column(name = "voted_count")
    private Integer votedCount;

    @Column(name = "option_text", length = 255)
    private String optionText;

    @Column(name = "file_id")
    private Long fileId;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "poll_id", insertable = false, updatable = false)
    private Poll poll;
}
