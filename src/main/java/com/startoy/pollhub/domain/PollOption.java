package com.startoy.pollhub.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ph_option")
public class PollOption  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // FetchType.LAZY : 관련 엔티티를 실제로 사용할 때까지 로딩을 지연시켜 성능을 최적화
    @JoinColumn(name = "poll_id")
    @ToString.Exclude // Lombok에 의한 toString() 메서드 생성 시, 양방향 관계 필드를 제외하여, 해당 필드를 숨기고 순환 참조 방지
    @JsonIgnore // Jackson으로 JSON 직렬화/역직렬화 시, 순환 관계가 있는 필드를 호출하지 않고 무시하여, 무한 재귀 호출 방지
    private Poll poll;

    @Column(name = "voted_count")
    private Integer votedCount;

    //@NotEmpty(message = "옵션 설명은 비어 있을 수 없습니다.")
    @Column(name = "option_text", length = 255)
    private String optionText;


    // option 에 첨부될 사진등에 대한 파일 형식 검사(추후에 영상 및 gif 도 추가하면 될 듯합니다.)
   // @Pattern(regexp = ".*\\.(jpg|png|jpeg)$", message = "파일 형식은 JPG, PNG, JPEG 만 허용됩니다.")
    @Column(name = "file_id")
    private UUID fileId;


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


}
