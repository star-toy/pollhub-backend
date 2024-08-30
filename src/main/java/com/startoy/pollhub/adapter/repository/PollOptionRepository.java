package com.startoy.pollhub.adapter.repository;

import com.startoy.pollhub.domain.PollOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollOptionRepository extends JpaRepository<PollOption, Long> {
}
