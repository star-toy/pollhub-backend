package com.startoy.pollhub.adapter.controller;

import com.startoy.pollhub.domain.Poll;
import com.startoy.pollhub.usecase.PollService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Operation(summary = "Get all polls")
    public List<Poll> getAllPolls() {
        return pollService.getAllPolls();
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
