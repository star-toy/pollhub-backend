package world.startoy.polling.usecase.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// 투표 결과를 클라이언트에게 제공할 때 사용
public class OptionVoteRateDTO {
    private String pollOptionUid;  // PollOption의 UID (UUID 형태)
    private String optionText;     // PollOption의 텍스트 (선택지의 내용)
    private Long votes;            // 해당 옵션에 대한 투표 수
    private Double voteRate;       // 해당 옵션의 투표율 (%)
}