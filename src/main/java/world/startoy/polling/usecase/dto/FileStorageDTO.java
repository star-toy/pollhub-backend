package world.startoy.polling.usecase.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileStorageDTO {
    @NotNull
    private String fileUid;
    @NotNull
    private String fileFullName; // 파일명+uid

}