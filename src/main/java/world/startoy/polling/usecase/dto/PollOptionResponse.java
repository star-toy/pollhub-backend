package world.startoy.polling.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollOptionResponse {
    private String pollOptionUid;
    private int pollOptionSeq;
    private String pollOptionText;
    private int votedCount; // 득표수
    private String fileUid;
    private String fileFullName;
}
