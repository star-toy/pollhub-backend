package com.startoy.pollhub.repository;

import com.startoy.pollhub.adapter.repository.PostRepository;
import com.startoy.pollhub.domain.Post;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@Log4j2
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    public void testSave() {
        Post post = Post.builder()
                .id(1L)
                .title("title")
                .fileId(1L)
                .isDeleted(false)
                .createdBy("gusdud")
                .build();
        postRepository.save(post);
    }



}
