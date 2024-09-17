package com.startoy.pollhub.adapter.repository;

import com.startoy.pollhub.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    // 특정 투표에 특정 ip 사용자 투표 정보 조회
    List<Vote> findByPollIdAndVoterIp(Long pollId, String voterIp);
}
