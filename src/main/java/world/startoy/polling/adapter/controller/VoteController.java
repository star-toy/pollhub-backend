package world.startoy.polling.adapter.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import world.startoy.polling.usecase.UserService;
import world.startoy.polling.usecase.VoteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import world.startoy.polling.usecase.dto.VoteDTO;
import world.startoy.polling.usecase.dto.VoteOptionRateDTO;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/vote")
@Tag(name = "투표 행위 Vote", description = "투표 행위 관리 API")
public class VoteController {

    private final VoteService voteService;
    private final UserService userService;


/*    @PostMapping
    public ResponseEntity<String> submitVote(@RequestParam Long pollId, @RequestParam Long optionId, HttpServletRequest request) {
        String voterIp = userService.getClientIp(request);

        try {
            voteService.submitVote(pollId, optionId, voterIp);
            return ResponseEntity.ok("투표 완료");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }*/

    @PostMapping("/{pollUid}")
    @Operation(summary = "투표와 동시에 투표율 조회")
    public ResponseEntity<List<VoteOptionRateDTO>> submitAndGetVoteRates(String pollUid, @RequestBody VoteDTO voteDTO) {
        // 1. 투표 제출
        voteService.submitVote(pollUid, voteDTO);

        // 2. 투표율 조회
        List<VoteOptionRateDTO> voteRates = voteService.getVoteRates(voteDTO.getPollUid());

        // 3. 결과 반환
        return ResponseEntity.ok(voteRates);
    } //catch (IllegalStateException e) {
       // return ResponseEntity.badRequest().body(null);
    }

//}


