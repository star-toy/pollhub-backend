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

}
