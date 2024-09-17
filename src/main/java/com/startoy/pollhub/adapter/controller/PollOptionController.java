package com.startoy.pollhub.adapter.controller;

import com.startoy.pollhub.domain.Poll;
import com.startoy.pollhub.domain.PollOption;
import com.startoy.pollhub.usecase.PollOptionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@Log4j2
@RequestMapping("/v1/options")
public class PollOptionController {

    private final PollOptionService pollOptionService;


    // 특정 poll_id에 해당하는 투표 옵션 전체 조회
    @GetMapping("/poll/{pollId}") 
    @Operation(summary = "특정 poll_id에 해당하는 투표 옵션 전체 조회")
    public ResponseEntity<List<PollOption>> findByPollId(@PathVariable Long pollId) {
        List<PollOption> options = pollOptionService.findOptionsByPollId(pollId);

        if (options.isEmpty()) { // 유저가 투표는 생성하고 옵션은 생성하지 않았을 경우
            // 200 OK 상태와 빈 목록을 반환
            return ResponseEntity.ok(Collections.emptyList());
        }

        return ResponseEntity.ok(options);
    }

    //  특정 ID의 투표 옵션 조회
    @GetMapping("/{optionId}")
    @Operation(summary = "특정 투표 옵션 조회")
    public ResponseEntity<PollOption> findOptionById(@PathVariable Long optionId) {
        try {
            PollOption option = pollOptionService.findOptionById(optionId);
            return ResponseEntity.ok(option);

        } catch (EntityNotFoundException e) {
            log.error("특정 투표 옵션 조회 중 오류 발생: 해당 ID의 옵션을 찾을 수 없음. ID: {}", optionId);
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            log.error("투표 옵션 조회 중 알 수 없는 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 에러
        }
    }


    //새로운 투표 옵션 생성
    @PostMapping
    @Operation(summary = "새로운 투표 옵션 생성")
    public ResponseEntity<PollOption> createOption(@Valid @RequestBody PollOption option) {
        // PollOption 유효성 검증 후 서비스 호출
        PollOption createdOption = pollOptionService.createOption(option);

        // 201 Created 응답과 함께 생성된 PollOption 반환
        return ResponseEntity.status(201).body(createdOption);
    }


    // 특정 ID의 투표 옵션 수정
    @PutMapping("/{optionId}")
    @Operation(summary = "특정 투표 옵션 수정")
    public ResponseEntity<PollOption> updateOption(@PathVariable Long optionId, @Valid @RequestBody PollOption option) {
        try {
            PollOption updatedOption = pollOptionService.updateOption(optionId, option);

            // 수정된 옵션 반환
            return ResponseEntity.ok(updatedOption);

        } catch (EntityNotFoundException e) {
            // 옵션을 찾을 수 없을 때 예외 처리
            log.error("투표 옵션 수정 중 오류 발생: 해당 ID의 옵션을 찾을 수 없음. ID: {}", optionId);
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            // 기타 예외 처리
            log.error("투표 옵션 수정 중 오류가 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 오류 응답
        }
    }


    // 특정 ID의 투표 옵션 삭제
    @DeleteMapping("/{optionId}")
    @Operation(summary = "특정 투표 옵션 삭제")
    public  ResponseEntity<String> deleteOption(@PathVariable Long optionId) {
        try {
            pollOptionService.deleteOption(optionId);
            return ResponseEntity.ok("투표 옵션 삭제 완료.");

        } catch (EntityNotFoundException e) {
            log.error("투표 옵션 삭제 중 오류 발생: 해당 ID의 옵션을 찾을 수 없음. {}", e.getMessage());
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            log.error("투표 옵션 삭제 중 오류가 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("투표 옵션 삭제 중 오류가 발생."); // 500 응답 반환
        }
    }
}
