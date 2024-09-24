package world.startoy.polling.adapter.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import world.startoy.polling.common.Uploadable;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/files")
@Tag(name = "파일 업로드", description = "파일 업로드 API")
public class FileStorageController {

    private final FileStorageService fileStorageService;
    private final UserService userService;



    @PostMapping(value ="/upload" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestParam("uploadableType") String uploadableType,
            @RequestParam("uploadableId") Long uploadableId,
            HttpServletRequest request) {

        Map<String, String> response = new HashMap<>();

        try{
            // 파일 유효성 검사
            if (file == null || file.isEmpty()) {
                response.put("message", "File must not be null or empty");
                return ResponseEntity.badRequest().body(response); // 400 Bad Request
            }

            String uploaderIp = userService.getClientIp(request);

            // Uploadable 객체 생성 (Post 객체 예시)
            Uploadable uploadable;
            if ("Post".equals(uploadableType)) {
                uploadable = fileStorageService.getPostById(uploadableId); // 서비스 호출
            } else if ("PollOption".equals(uploadableType)) {
                uploadable = fileStorageService.getPollOptionById(uploadableId); // 서비스 호출
            } else {
                throw new IllegalArgumentException("Invalid uploadable type");
            }


            // 파일 저장
            fileStorageService.saveFile(file, uploadable, uploaderIp);

            // 정상 동작 완료
            response.put("message", "File uploaded successfully");
            return ResponseEntity.ok(response); // 200 OK

        } catch (IllegalArgumentException e) {
            // 잘못된 파일 형식 등 클라이언트 문제
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response); // 400 Bad Request

        } catch (IOException e) {
            // 파일 저장 중 발생한 IO 오류 (서버 문제)
            response.put("message", "Failed to store file due to server error");
            return ResponseEntity.status(500).body(response); // 500 Internal Server Error

        } catch (Exception e) {
            // 기타 예상하지 못한 서버 오류
            response.put("message", "An unexpected error occurred while uploading the file");
            return ResponseEntity.status(500).body(response); // 500 Internal Server Error
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