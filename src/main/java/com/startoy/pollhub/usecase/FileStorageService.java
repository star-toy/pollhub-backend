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
                Files.createDirectories(path); // 폴더 생성 실패 시 IOException 발생 가능
            }

            String fileId = UUID.randomUUID().toString();
            String fileName = file.getOriginalFilename(); // File Extension 포함 // ex) filename.png

            if (fileName == null || !fileName.contains(".")) {
                throw new FileValidationException("Invalid file name: The file name is either null or missing a file extension.");
            }

            // 파일 확장자 추출
            String[] fileNameParts = fileName.split("\\.");
            if (fileNameParts.length < 2) {
                throw new FileValidationException("File must have an extension");
            }

            String fileExtension = fileNameParts[1];

            // 중복 파일명 방지
            String fileFullName = fileNameParts[0] + "_" + fileId + "." + fileExtension; // ex) filename_uuid.png

            // 파일 저장
            Path filePath = path.resolve(fileFullName);
            Files.copy(file.getInputStream(), filePath); // 파일 저장 실패 시 IOException 발생

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

        } catch (FileValidationException e) {
            throw e; // 클라이언트 문제는 그대로 전달
        } catch (IOException e) {
            // 파일 저장 관련 오류는 서버 문제로 구분
            throw new FileStorageException("Failed to store file on the server", e);
        } catch (Exception e) {
            // 기타 예외 처리
            throw new FileStorageException("An unexpected error occurred while processing the file", e);
        }
    }

    // Custom Exception for file validation issues
    public static class FileValidationException extends RuntimeException {
        public FileValidationException(String message) {
            super(message);
        }
    }

    // Custom Exception for file storage issues
    public static class FileStorageException extends RuntimeException {
        public FileStorageException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
