package world.startoy.polling.usecase.dto;


import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {
    @NotNull
    private String postUid;
    @NotNull
    private String title;
    @NotNull
    private String createdBy;       // Post.createdBy
    @NotNull
    private LocalDateTime createdAt;// Post.createdAt
    @NotNull
    private String imageUrl;
}