package com.startoy.pollhub.usecase;

import com.startoy.pollhub.adapter.repository.FileStorageRepository;
import com.startoy.pollhub.domain.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final FileStorageRepository fileStorageRepository;
    private final String uploadDir = "/path/to/upload/directory";

    public FileStorage saveFile(MultipartFile file, String uploaderIp) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be null or empty");
        }

        try {

            // 폴더 존재 여부 확인
            if (!Files.exists(Path.of(uploadDir))) {
                Files.createDirectories(Path.of(uploadDir));
            }

            // 파일 확장자 추출
            String fileExtension = getFileExtension(file.getOriginalFilename());

            // 파일 저장
            String fileName = UUID.randomUUID().toString() + "." + fileExtension;
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            // FileStorage 객체 생성
            FileStorage fileStorage = FileStorage.builder()
                    .fileId(UUID.randomUUID())
                    .fileName(file.getOriginalFilename())
                    .fileFullName(fileName)
                    .filePath(filePath.toString())
                    .fileFullPath(filePath.toAbsolutePath().toString())
                    .fileExtension(fileExtension)
                    .isDeleted(false)
                    .createdAt(LocalDateTime.now())
                    .createdBy(uploaderIp)
                    .build();

            return fileStorageRepository.save(fileStorage);

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    private String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        if (lastIndexOfDot == -1) {
            return "";  // 파일 확장자가 없는 경우
        }
        return fileName.substring(lastIndexOfDot + 1);
    }
}
