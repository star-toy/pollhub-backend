package world.startoy.polling.usecase.dto;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String postUid;
    @NotNull
    private String title;
    @NotNull
    private List<PollDetailResponse> polls;
    @NotNull
    private String createdBy;       // Post.createdBy
    @NotNull
    private LocalDateTime createdAt;// Post.createdAt
    // 추후 @NotNull 추가해야함
    private String imageUrl;
}
