package world.startoy.polling.usecase.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter // @NotNull @Validated 를 이용하기 위해 getter를 명시적으로 추가
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteCreateRequest {
    @NotNull
    private String pollUid;
    @NotNull
    private String pollOptionUid;
    @NotNull
    private String voterIp;
    @NotNull
    private LocalDateTime createdAt;
}
