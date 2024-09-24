package world.startoy.polling.usecase.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileStorageDTO {

    private String fileUid;
    private String fileName;
    //private String fileUrl;  // S3에 저장된 파일의 URL
    //private String fileExtension;
    //private String createdBy;
    //private boolean isDeleted;

}