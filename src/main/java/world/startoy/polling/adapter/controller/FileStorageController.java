package world.startoy.polling.adapter.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import world.startoy.polling.common.Uploadable;
import world.startoy.polling.config.CloudFrontConfig;
import world.startoy.polling.usecase.FileStorageService;
import world.startoy.polling.usecase.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import world.startoy.polling.usecase.dto.FileStorageDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/files")
@Tag(name = "파일 업로드", description = "파일 업로드 API")
public class FileStorageController {

    private final FileStorageService fileStorageService;
    private final UserService userService;
    private final CloudFrontConfig cloudFrontConfig;
    private  FileStorageDTO fileStorageDTO;




    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileStorageDTO> uploadFile(
            @RequestPart("file") MultipartFile file) {

        try {
            // 파일 유효성 검사
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("File must not be null or empty");
            }

            // FileStorageService의 saveFile 메서드 호출하여 파일 S3 업로드
            FileStorageDTO fileStorageDTO = fileStorageService.saveFile(file);

            // FileStorageDTO 반환
            return ResponseEntity.ok(fileStorageDTO);

        } catch (IllegalArgumentException e) {
            // 클라이언트 오류 처리
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(null); // FileStorageDTO 대신 null 반환
        } catch (Exception e) {
            // 기타 서버 오류 처리
            Map<String, String> response = new HashMap<>();
            response.put("message", "An error occurred while uploading the file");
            return ResponseEntity.status(500).body(null); // FileStorageDTO 대신 null 반환
        }
    }


    // fileUid를 이용하여 파일 URL을 조회하는 엔드포인트
    @GetMapping("/{fileUid}")
    public ResponseEntity<FileStorageDTO> getFileByUid(@PathVariable String fileUid) {
        try {
            FileStorageDTO fileStorageDto = fileStorageService.getFileDtoByUid(fileUid);

            return ResponseEntity.ok(fileStorageDto); // 200 OK, FileStorageDTO 반환
        } catch (IllegalArgumentException e) {
            // 잘못된 UID 요청 (클라이언트 오류)
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        } catch (Exception e) {
            // 기타 서버 오류
            return ResponseEntity.status(500).body(null); // 500 Internal Server Error
        }
    }
}