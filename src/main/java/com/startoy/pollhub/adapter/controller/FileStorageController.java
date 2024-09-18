package com.startoy.pollhub.adapter.controller;

import com.startoy.pollhub.usecase.FileStorageService;
import com.startoy.pollhub.usecase.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/files")
@Tag(name = "파일 업로드", description = "파일 업로드 API")
public class FileStorageController {

    private final FileStorageService fileStorageService;
    private final UserService userService;


    @PostMapping("/upload")
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadFile(
            @RequestPart("file") MultipartFile file,
            HttpServletRequest request) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("File must not be null or empty");
        }

        String uploaderIp = userService.getClientIp(request);

        try {
            // 파일 저장
            fileStorageService.saveFile(file, uploaderIp);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while uploading the file");
        }
    }

}
