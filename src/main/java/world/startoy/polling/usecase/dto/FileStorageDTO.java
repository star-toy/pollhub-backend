package world.startoy.polling.usecase.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
public class FileStorageDTO {

    private String fileUid;
    private String fileFullName; // 파일명+uid
    @Setter
    private String fileUrl; // cloudfrontUrl+fileFullName


}