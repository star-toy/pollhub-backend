package world.startoy.polling.usecase.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileStorageDTO {

    private String fileUid;
    private String fileFullName; // 파일명+uid

}