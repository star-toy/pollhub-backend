package com.startoy.pollhub.adapter.repository;

import com.startoy.pollhub.domain.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    // 특정 게시글에 포함된 모든 투표 조회
    List<Poll> findByPostId(Long postId);
}
