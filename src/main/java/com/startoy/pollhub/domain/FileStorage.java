package com.startoy.pollhub.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ph_file_storage")
public class FileStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id", nullable = false, updatable = false)
    private Long fileId;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_content_type", nullable = false)
    private FileContentType fileContentType;

    @Column(name = "file_path", length = 1000, nullable = false)
    private String filePath;

    @Column(name = "file_full_path", length = 1000, nullable = false)
    private String fileFullPath;

    @Column(name = "file_name", length = 255, nullable = false)
    private String fileName;

    @Column(name = "file_full_name", length = 255, nullable = false)
    private String fileFullName;

    @Column(name = "file_content", length = 255, nullable = false)
    private String fileContent;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "created_by", length = 20, nullable = false)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    public enum FileContentType {
        IMAGE, VIDEO, URL
    }
}
