package com.startoy.pollhub.adapter.repository;

import com.startoy.pollhub.domain.PollOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;

@Repository
public interface PollOptionRepository extends JpaRepository<PollOption, Long> {
    // 특정 투표에 포함된 모든 선택지 조회
    List<PollOption> findByPollId(Long pollId);
}
