package world.startoy.polling.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import world.startoy.polling.common.Uploadable;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pl_poll_option")
public class PollOption implements Uploadable {

    @Override
    public String getLinkedUid() {
        return this.pollOptionUid;
    }

    @Override
    public String getUploadableType() {
        return "PollOption";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poll_option_id", nullable = false)
    private Long id; // 데이터베이스 식별자 (백엔드 내부에서 사용)

    @NotNull
    @Column(name = "poll_option_uid", unique = true, nullable = false, length = 36)
    private String pollOptionUid; // UUID 형태 (API 응답에서 사용)

    @ManyToOne(fetch = FetchType.LAZY) // FetchType.LAZY : 관련 엔티티를 실제로 사용할 때까지 로딩을 지연시켜 성능을 최적화
    @JoinColumn(name = "poll_id")
    @ToString.Exclude // Lombok에 의한 toString() 메서드 생성 시, 양방향 관계 필드를 제외하여, 해당 필드를 숨기고 순환 참조 방지
    @JsonBackReference // JsonIgnore 어노테이션 적용 시 polloption post api 에서 poll_id 를 참조하지 못하는 오류 발생.
    private Poll poll;

    @NotNull
    @Column(name = "poll_option_seq", nullable = false)
    private int pollOptionSeq;

    @Column(name = "poll_option_text", length = 255)
    private String pollOptionText;

    @ManyToOne
    @JoinColumn(name = "file_id", referencedColumnName = "file_id")
    private FileStorage file;

    @NotNull
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "created_by", nullable = false, length = 20)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;
}