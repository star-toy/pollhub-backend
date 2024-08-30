package com.startoy.pollhub.adapter.controller;

import com.startoy.pollhub.domain.PollOption;
import com.startoy.pollhub.usecase.PollOptionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/options")
public class PollOptionController {

    private final PollOptionService pollOptionService;

    public PollOptionController(PollOptionService pollOptionService) {
        this.pollOptionService = pollOptionService;
    }

    @GetMapping
    @Operation(summary = "Get all poll options")
    public List<PollOption> getAllOptions() {
        return pollOptionService.getAllOptions();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a poll option by ID")
    public Optional<PollOption> getOptionById(@PathVariable Long id) {
        return pollOptionService.getOptionById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new poll option")
    public PollOption createOption(@RequestBody PollOption option) {
        return pollOptionService.createOption(option);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a poll option by ID")
    public PollOption updateOption(@PathVariable Long id, @RequestBody PollOption option) {
        return pollOptionService.updateOption(id, option);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a poll option by ID")
    public void deleteOption(@PathVariable Long id) {
        pollOptionService.deleteOption(id);
    }
}
