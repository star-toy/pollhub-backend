package com.startoy.pollhub.usecase;

import com.startoy.pollhub.adapter.repository.VoteRepository;
import com.startoy.pollhub.domain.Vote;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    public boolean hasVoted(Long pollId, String voterIp) {
        return !voteRepository.findByPollIdAndVoterIp(pollId, voterIp).isEmpty();
    }

    public Vote submitVote(Long pollId, Long optionId, String voterIp) {
        if (hasVoted(pollId, voterIp)) {
            throw new IllegalStateException("이미 투표하셨습니다.");
        }

        Vote vote = new Vote();
        vote.setPollId(pollId);
        vote.setOptionId(optionId);
        vote.setVoterIp(voterIp);
        return voteRepository.save(vote);
    }

    public String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
