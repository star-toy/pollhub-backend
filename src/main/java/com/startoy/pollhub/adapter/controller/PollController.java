package com.startoy.pollhub.adapter.controller;

import com.startoy.pollhub.domain.Poll;
import com.startoy.pollhub.usecase.PollService;
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
@RequestMapping("/api/polls")
public class PollController {

    @Autowired
    private final PollService pollService;

    @GetMapping
    @Operation(summary = "투표 전체 조회")
    public List<Poll> findAllPolls() {
        return pollService.findAllPolls();
    }

    
    // 투표 생성
    @PostMapping
    @Operation(summary = "새로운 투표 생성")
    public ResponseEntity<Poll> createPoll(@Valid @RequestBody Poll poll) {
        // Poll 객체의 유효성을 검증하고, 유효성 검사가 성공하면 서비스 호출
        Poll createdPoll = pollService.createPoll(poll);

        // 201 Created 응답과 함께 생성된 Poll 객체를 반환
        return ResponseEntity.status(201).body(createdPoll);
    }

    @PutMapping("/{id}")
    @Operation(summary = "투표 수정")
    public Poll updatePoll(@PathVariable Long id, @RequestBody Poll poll) {
        return pollService.updatePoll(id, poll);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "투표 삭제")
    public void deletePoll(@PathVariable Long id) {
        pollService.deletePoll(id);
    }
}
