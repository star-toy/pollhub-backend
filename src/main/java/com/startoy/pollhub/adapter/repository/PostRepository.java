package com.startoy.pollhub.adapter.repository;

import com.startoy.pollhub.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
