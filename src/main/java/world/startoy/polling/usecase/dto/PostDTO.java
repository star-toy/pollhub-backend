package world.startoy.polling.usecase.dto;


import lombok.*;
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {

    private String postUid;
    private String title;
    private String created_by;
    private String created_at;

}


