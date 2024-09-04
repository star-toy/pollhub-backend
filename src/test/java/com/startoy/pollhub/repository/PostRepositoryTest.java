package com.startoy.pollhub.repository;

import com.startoy.pollhub.adapter.repository.PostRepository;
import com.startoy.pollhub.domain.Post;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.LongStream;

@SpringBootTest
@Log4j2
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    public void testSave() {
        LongStream.rangeClosed(1L,20L).forEach(i -> {
            Post post = Post.builder()
                    .title("title" + i)
                    .fileId(i)
                    .isDeleted(false)
                    .createdBy("createdBy" + i)
                    .build();
            Post result = postRepository.save(post);  //JPA는 자동으로 만들어주기 때문에 내가 만들지 않은 save 메소드도 나온다.
            log.info(result);
        });
    }

    @Test
    public void testSelect() {
        Long pid = 2L;

        Optional<Post> result = postRepository.findById(pid);  //optional Type으로 받아서 처리해야 함
        Post post = result.orElseThrow();
        log.info(post.getTitle());
    }

    @Test
    public void testDelete() {
        Long pid = 1L;
        Optional<Post> result = postRepository.findById(pid);
        Post post = result.orElseThrow();
        postRepository.delete(post);
    }

}
