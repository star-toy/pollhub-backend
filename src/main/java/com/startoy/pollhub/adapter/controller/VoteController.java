package com.startoy.pollhub.adapter.controller;

import com.startoy.pollhub.usecase.VoteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/vote")
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<String> submitVote(@RequestParam Long pollId, @RequestParam Long optionId, HttpServletRequest request) {
        String voterIp = getClientIp(request);

        try {
            voteService.submitVote(pollId, optionId, voterIp);
            return ResponseEntity.ok("투표 완료");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String getClientIp(HttpServletRequest request) {
        return voteService.getClientIp(request);  // 서비스에서 IP 주소를 가져오는 메소드 호출
    }
}

