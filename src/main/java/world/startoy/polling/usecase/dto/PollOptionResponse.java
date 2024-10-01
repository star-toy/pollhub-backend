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
    private String fileUid;
    @NotNull
    private String fileName;
}
