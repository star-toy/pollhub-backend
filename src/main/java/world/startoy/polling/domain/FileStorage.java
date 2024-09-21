package world.startoy.polling.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pl_file_storage")
public class FileStorage {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "file_uid", unique = true, nullable = false, length = 36)
    private String fileUid;

    @NotNull
    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    @NotNull
    @Column(name = "file_full_name", nullable = false, length = 255)
    private String fileFullName;

    @NotNull
    @Column(name = "file_path", nullable = false, length = 1000)
    private String filePath;

    @NotNull
    @Column(name = "file_full_path", nullable = false, length = 1000)
    private String fileFullPath;

    @NotNull
    @Column(name = "file_extension", nullable = false, length = 10)
    private String fileExtension;

    @NotNull
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "created_by", nullable = false, length = 20)
    private String createdBy;


    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;
}
