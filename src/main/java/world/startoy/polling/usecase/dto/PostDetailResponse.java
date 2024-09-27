package world.startoy.polling.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetailResponse {
    private String postUid;
    private String title;
    private String fileUid;
    private String fileFullName;
    private List<PollDetailResponse> polls;
    private LocalDateTime createdAt;
    private String createdBy;
}
