package com.startoy.pollhub.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ph_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false, updatable = false)
    private Long id;

    // 타이틀 제목
    @Column(nullable = false, length = 200)
    private String title;

    // 타이틀 이미지
    @Column(name = "file_id")
    private UUID fileId;

    // 삭제 여부
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    // 생성 날짜
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 작성자
    @Column(name = "created_by", length = 20, nullable = false)
    private String createdBy;

    // 수정 날짜
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 수정자
    @Column(name = "updated_by", length = 20)
    private String updatedBy;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Poll> polls;

}
