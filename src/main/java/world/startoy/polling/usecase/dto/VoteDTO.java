package world.startoy.polling.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 투표 정보를 전달받기 위한 DTO 클래스
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteDTO {
    private String voteUid;    // 투표의 유일한 식별자
    private Long pollId;       // Poll 엔티티의 ID (Foreign Key)
    private Long optionId;     // PollOption 엔티티의 ID (Foreign Key)
    private String voterIp;    // 투표자 IP
}