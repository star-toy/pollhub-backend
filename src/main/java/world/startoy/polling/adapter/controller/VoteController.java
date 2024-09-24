package world.startoy.polling.adapter.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import world.startoy.polling.usecase.UserService;
import world.startoy.polling.usecase.VoteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import world.startoy.polling.usecase.dto.OptionVoteRateDTO;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/vote")
@Tag(name = "투표 행위 Vote", description = "투표 행위 관리 API")
public class VoteController {

    private final VoteService voteService;
    private final UserService userService;


    @PostMapping
    public ResponseEntity<String> submitVote(@RequestParam Long pollId, @RequestParam Long optionId, HttpServletRequest request) {
        String voterIp = userService.getClientIp(request);

        try {
            voteService.submitVote(pollId, optionId, voterIp);
            return ResponseEntity.ok("투표 완료");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 투표율 조회
    @GetMapping("/rate")
    @Operation(summary = "특정 투표의 옵션들 투표율 조회")
    public ResponseEntity<List<OptionVoteRateDTO>> getVoteRate(@RequestParam Long pollId) {
        List<OptionVoteRateDTO> voteRates = voteService.getVoteRates(pollId);
        return ResponseEntity.ok(voteRates);
    }
}