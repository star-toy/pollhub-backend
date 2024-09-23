package world.startoy.polling.adapter.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.startoy.polling.usecase.UserService;
import world.startoy.polling.usecase.VoteService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/vote")
@Tag(name = "투표 행위 Vote", description = "투표 행위 관리 API")
public class VoteController {

    private final VoteService voteService;
    private final UserService userService;


}
