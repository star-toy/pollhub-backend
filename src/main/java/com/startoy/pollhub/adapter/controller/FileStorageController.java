package com.startoy.pollhub.adapter.controller;

import com.startoy.pollhub.usecase.FileStorageService;
import com.startoy.pollhub.usecase.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/files")
@Tag(name = "파일 업로드", description = "파일 업로드 API")
public class FileStorageController {

    private final FileStorageService fileStorageService;
    private final UserService userService;


    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, String>> uploadFile(
            @RequestPart("file") MultipartFile file,
            HttpServletRequest request) {

        Map<String, String> response = new HashMap<>();

        if (file == null || file.isEmpty()) {
            response.put("message", "File must not be null or empty");
            return ResponseEntity.badRequest().body(response);
        }

        String uploaderIp = userService.getClientIp(request);

        try {
            // 파일 저장
            fileStorageService.saveFile(file, uploaderIp);
            response.put("message", "File uploaded successfully");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("message", "An error occurred while uploading the file");
            return ResponseEntity.status(500).body(response);
        }
    }

}
