package com.startoy.pollhub.adapter.controller;

import com.startoy.pollhub.domain.Poll;
import com.startoy.pollhub.usecase.PollService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    private final PollService pollService;

    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping
    @Operation(summary = "Get all polls")
    public List<Poll> getAllPolls() {
        return pollService.getAllPolls();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a poll by ID")
    public Optional<Poll> getPollById(@PathVariable Long id) {
        return pollService.getPollById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new poll")
    public Poll createPoll(@RequestBody Poll poll) {
        return pollService.createPoll(poll);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a poll by ID")
    public Poll updatePoll(@PathVariable Long id, @RequestBody Poll poll) {
        return pollService.updatePoll(id, poll);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a poll by ID")
    public void deletePoll(@PathVariable Long id) {
        pollService.deletePoll(id);
    }
}
