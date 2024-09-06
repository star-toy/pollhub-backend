package com.startoy.pollhub.adapter.controller;

import com.startoy.pollhub.domain.PollOption;
import com.startoy.pollhub.usecase.PollOptionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/options")
public class PollOptionController {
    @Autowired
    private final PollOptionService pollOptionService;

    @GetMapping
    @Operation(summary = "투표 옵션 전체 조회")
    public List<PollOption> findAllOptions() {
        return pollOptionService.findAllOptions();
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 투표 옵션 조회")
    public Optional<PollOption> getOptionById(@PathVariable Long id) {
        return pollOptionService.getOptionById(id);
    }

    @PostMapping
    @Operation(summary = "새로운 투표 옵션 생성")
    public ResponseEntity<PollOption> createOption(@Valid @RequestBody PollOption option) {
        // PollOption 유효성 검증 후 서비스 호출
        PollOption createdOption = pollOptionService.createOption(option);

        // 201 Created 응답과 함께 생성된 PollOption 반환
        return ResponseEntity.status(201).body(createdOption);
    }

    @PutMapping("/{id}")
    @Operation(summary = "특정 투표 옵션 수정")
    public PollOption updateOption(@PathVariable Long id, @RequestBody PollOption option) {
        return pollOptionService.updateOption(id, option);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a poll option by ID")
    public void deleteOption(@PathVariable Long id) {
        pollOptionService.deleteOption(id);
    }
}
