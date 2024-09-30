package world.startoy.polling.usecase.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter // @NotNull @Validated 를 이용하기 위해 getter를 명시적으로 추가
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PollOptionCreateRequest {
    @NotNull
    private int pollOptionSeq;
    @NotNull
    private String pollOptionText;
    @NotNull
    private String fileUid;
    @NotNull
    private String fileName;
}
