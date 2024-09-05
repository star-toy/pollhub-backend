package com.startoy.pollhub.repository;

import com.startoy.pollhub.adapter.repository.PollRepository;
import com.startoy.pollhub.adapter.repository.PostRepository;
import com.startoy.pollhub.adapter.repository.PollOptionRepository;
import com.startoy.pollhub.domain.Poll;
import com.startoy.pollhub.domain.Post;
import com.startoy.pollhub.domain.PollOption;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@Log4j2
public class PollRepositoryTest {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PollOptionRepository pollOptionRepository;

/*    @Test
    void createPollWithPostAndOptions() {
        // Post 객체 생성 및 저장
        Post post = new Post();
        post.setTitle("테스트제목");
        post.setPollId(1L);
        post.setIsDeleted(false);
        post.setCreatedBy("user");
        post.setFileId(1L);

        Post savedPost = postRepository.save(post);
        Assertions.assertThat(savedPost).isNotNull();
        Assertions.assertThat(savedPost.getId()).isNotNull();

        // Poll 객체 생성 및 저장
        Poll poll = new Poll();
        poll.setTitle("Test Poll");
        poll.setExpiresAt(LocalDateTime.now().plusDays(7));
        poll.setIsDeleted(false);
        poll.setCreatedAt(LocalDateTime.now());
        poll.setCreatedBy("Poll test");
        poll.setPost(savedPost);

        savedPost.addPoll(poll);

        // PollOption 객체 생성 및 저장
        PollOption option1 = new PollOption();
        option1.setOptionText("Option 1");
        option1.setVotedCount(0);
        option1.setIsDeleted(false);
        option1.setCreatedBy("Option test");
        poll.addOption(option1);

        // Post 저장을 통해 Poll과 PollOption 저장
        postRepository.save(savedPost);

        // 검증
        Assertions.assertThat(poll.getOptions()).hasSize(1);
        log.info("Saved Post with Poll and Options: {}", savedPost);
    }*/


    //void delete
}
