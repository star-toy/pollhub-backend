package world.startoy.polling.adapter.controller;

import world.startoy.polling.domain.PollOption;
import world.startoy.polling.usecase.PollOptionService;
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
@RequestMapping("/v1/options")
@Tag(name = "투표 선택지 Option", description = "투표 옵션 관리 API")
public class PollOptionController {

    private final PollOptionService pollOptionService;

}