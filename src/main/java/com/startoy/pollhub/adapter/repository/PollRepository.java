package com.startoy.pollhub.adapter.repository;

import com.startoy.pollhub.domain.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll, Long> {
}
