package com.startoy.pollhub.adapter.repository;

import com.startoy.pollhub.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findByPollIdAndVoterIp(Long pollId, String voterIp);
}
