package world.startoy.polling.usecase.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Getter // @NotNull @Validated 를 이용하기 위해 getter를 명시적으로 추가
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {
    @NotNull
    private String title;
    @NotNull
    private List<PollDTO> polls;
}
