package world.startoy.polling.adapter.controller;

import world.startoy.polling.domain.Poll;
import world.startoy.polling.usecase.PollService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Log4j2
@RequestMapping("/v1/polls")
@Tag(name = "투표 Poll", description = "투표 관리 API")
public class PollController {

    private final PollService pollService;


    // 투표 전체 조회
    @GetMapping
    @Operation(summary = "투표 전체 조회")
    public ResponseEntity<List<Poll>> findAllPolls() {
        List<Poll> polls = pollService.findAllPolls();

        if (polls.isEmpty()) { // 유저가 post는 생성하고 poll은 생성하지 않았을 경우
            // 200 OK 상태와 빈 목록을 반환
            return ResponseEntity.ok(Collections.emptyList());
        }

        return ResponseEntity.ok(polls); // 투표 목록이 존재할 경우 정상적으로 반환
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


    // 특정 ID의 투표 수정
    @PutMapping("/{pollId}")
    @Operation(summary = "투표 수정")
    public ResponseEntity<Poll> updatePoll(@PathVariable Long pollId, @RequestBody Poll poll) {
        try {
            Poll updatedPoll = pollService.updatePoll(pollId, poll);
            return ResponseEntity.ok(updatedPoll);

        } catch (EntityNotFoundException e) {

            log.error("투표 수정 중 오류 발생: 해당 ID의 투표를 찾을 수 없음. {}",e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("투표 수정 중 오류가 발생: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


    // 특정 ID의 투표 삭제
    @DeleteMapping("/{pollId}")
    @Operation(summary = "투표 삭제")
    public ResponseEntity<String> deletePoll(@PathVariable Long pollId) {
        try {
            pollService.deletePoll(pollId);
            return ResponseEntity.ok("투표 삭제 완료.");

        } catch (EntityNotFoundException e) {
            log.error("투표 삭제 중 오류 발생: 해당 ID의 투표를 찾을 수 없음. {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("투표 삭제 중 오류가 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("투표 삭제 중 오류가 발생."); // 500
        }
    }

}
