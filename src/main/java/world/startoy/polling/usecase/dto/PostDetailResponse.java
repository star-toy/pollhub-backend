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
    private List<PollDetailResponse> polls;
    private String fileUid;
    private String fileFullName;
    private String createdBy;       // Post.createdBy
    private LocalDateTime createdAt;// Post.createdAt

}
