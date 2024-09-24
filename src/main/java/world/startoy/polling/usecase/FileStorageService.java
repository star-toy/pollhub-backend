package world.startoy.polling.usecase;

import world.startoy.polling.adapter.repository.FileStorageRepository;
import world.startoy.polling.domain.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final FileStorageRepository fileStorageRepository;
    private final S3Service s3Service;

    // 파일 업로드
    @Transactional
    public FileStorage saveFile(MultipartFile file, String uploaderIp) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be null or empty");
        }

        try {
            String fileUid = UUID.randomUUID().toString();
            String fileName = file.getOriginalFilename();

            if (fileName == null || !fileName.contains(".")) {
                throw new FileValidationException("Invalid file name: The file name is either null or missing a file extension.");
            }

            String fileExtension = getFileExtension(fileName);
            String fileFullName = String.format("%s_%s.%s", fileName.substring(0, fileName.lastIndexOf('.')), fileUid, fileExtension);

            // S3에 파일 업로드
            String fileUrl = s3Service.uploadFile(file, fileFullName);
            System.out.println("fileUrl : " + fileUrl);


            FileStorage fileStorage = FileStorage.builder()
                    .fileUid(fileUid)
                    .fileName(fileName)
                    .fileFullName(fileFullName)
                    .filePath(fileUrl)
                    .fileFullPath(fileUrl)
                    .fileExtension(fileExtension)
                    .isDeleted(false)
                    .createdAt(LocalDateTime.now())
                    .createdBy(uploaderIp)
                    .build();


            FileStorage savedFileStorage = fileStorageRepository.save(fileStorage);
            System.out.println("FileStorage after save: " + savedFileStorage);

            return savedFileStorage;

        } catch (FileValidationException e) {
            throw e;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new FileStorageException("Failed to store file on S3", e);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new FileStorageException("An unexpected error occurred while processing the file", e);
        }
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
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