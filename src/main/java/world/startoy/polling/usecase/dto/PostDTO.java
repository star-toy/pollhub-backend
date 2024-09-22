package world.startoy.polling.usecase.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {
    private String postUid;
    private String title;
    private String created_by;
}
