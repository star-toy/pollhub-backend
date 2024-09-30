package world.startoy.polling.adapter.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.startoy.polling.usecase.UserService;
import world.startoy.polling.usecase.VoteService;
import world.startoy.polling.usecase.dto.VoteCreateRequest;
import world.startoy.polling.usecase.dto.VoteCreateResponse;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/vote")
@Tag(name = "Vote", description = "Vote 투표 행위 기록 API")
public class VoteController {

    private final VoteService voteService;
    private final UserService userService;


    @PostMapping
    public VoteCreateResponse createVote(@RequestBody VoteCreateRequest request, HttpServletRequest httpRequest) {
        String voterIp = userService.getClientIp(httpRequest);
        return voteService.createVote(request, voterIp);
    }

}

