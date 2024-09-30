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
    private int votedCount; // 득표수
    @NotNull
    private String fileUid;
    @NotNull
    private String fileFullName;
}
