package world.startoy.polling.usecase.dto;


import lombok.*;

import java.time.LocalDateTime;

@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {
    private String postUid;
    private String title;
    private String createdBy;
    private LocalDateTime createdAt;
}