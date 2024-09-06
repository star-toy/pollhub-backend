package com.startoy.pollhub.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ph_poll")
public class Poll{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poll_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "poll_category", nullable = false, length = 255)
    private String pollCategory;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "created_by", length = 20, nullable = false)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @ManyToOne(fetch = FetchType.LAZY) // FetchType.LAZY : 관련 엔티티를 실제로 사용할 때까지 로딩을 지연시켜 성능을 최적화
    @JoinColumn(name = "post_id")
    @ToString.Exclude // Lombok에 의한 toString() 메서드 생성 시, 양방향 관계 필드를 제외하여, 해당 필드를 숨기고 순환 참조 방지
    @JsonIgnore // Jackson으로 JSON 직렬화/역직렬화 시, 순환 관계가 있는 필드를 호출하지 않고 무시하여, 무한 재귀 호출 방지
    private Post post;

    @OneToMany(mappedBy = "poll", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PollOption> options;
}
