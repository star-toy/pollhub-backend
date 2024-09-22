package world.startoy.polling.usecase.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PostDetailResponse {
    private String postUid;
    private String title;
    private List<PollDTO> polls;
    private LocalDateTime createdAt;
    private String createdBy;
}
