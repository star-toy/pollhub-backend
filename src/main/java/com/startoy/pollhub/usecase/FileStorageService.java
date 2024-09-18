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
            Path path = Path.of(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String fileId = UUID.randomUUID().toString();
            String fileName = file.getOriginalFilename(); // File Extension 포함 // ex) filename.png

            if(fileName == null || !fileName.contains(".")) {
                throw new IllegalArgumentException("Invalid file name: The file name is either null or missing a file extension.");
            }

            // 파일 확장자 추출
            String fileExtension = fileName.split("\\.")[1];
            // 중복 파일명 방지
            String fileFullName = fileName.split("\\.")[0] + "_" + fileId + "." + fileExtension; // ex) filename_uuid.png'

            // 파일 저장
            Path filePath = path.resolve(fileFullName);
            Files.copy(file.getInputStream(), filePath);

            // FileStorage 객체 생성
            FileStorage fileStorage = FileStorage.builder()
                    .fileId(fileId)
                    .fileName(fileName)
                    .fileFullName(fileFullName)
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

}
