package com.startoy.pollhub.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor // 모든 필드를 받는 생성자 추가
@NoArgsConstructor  // 기본 생성자 추가 -> 롬복의 Data 어노테이션이 기본 생성자를 자동으로 추가해주지 않으므로 명시적으로 추가해줘야함.
@Table(name = "ph_post")
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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 20, nullable = false)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Poll> polls;
//    private Set<Poll> polls = new HashSet<>(); // null 이 되지 않도록 hashSet으로 초기화.

    // 연관관계 편의 메서드 추가
/*    public void addPoll(Poll poll) {
        polls.add(poll);
        poll.setPost(this);
    }

    public void removePoll(Poll poll) {
        polls.remove(poll);
        poll.setPost(null); // 연결을 해제하여 orphanRemoval이 작동하도록 함
    }*/
}
