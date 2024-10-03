package world.startoy.polling.usecase.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollOptionResponse {
    @NotNull
    private String pollOptionUid;
    @NotNull
    private int pollOptionSeq;
    @NotNull
    private String pollOptionText;
    @NotNull
    private Long votedCount; // 득표수 // JPQL 쿼리에서 COUNT(v.id)는 Long 타입을 반환
    @NotNull
    private boolean hasVoted; // 해당 옵션 사용자 투표 여부
    @NotNull
    private String imageUrl; // CloudFront URL을 포함한 전체 이미지 URL


    public PollOptionResponse(String pollOptionUid, int pollOptionSeq, String pollOptionText, Long votedCount, String imageUrl) {
        this.pollOptionUid = pollOptionUid;
        this.pollOptionSeq = pollOptionSeq;
        this.pollOptionText = pollOptionText;
        this.votedCount = votedCount;
        this.imageUrl = imageUrl;
    }
}
