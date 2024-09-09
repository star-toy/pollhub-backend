package com.startoy.pollhub.repository;

import com.startoy.pollhub.adapter.repository.PollRepository;
import com.startoy.pollhub.adapter.repository.PostRepository;
import com.startoy.pollhub.adapter.repository.PollOptionRepository;
import com.startoy.pollhub.domain.Poll;
import com.startoy.pollhub.domain.Post;
import com.startoy.pollhub.domain.PollOption;
import com.startoy.pollhub.usecase.PollService;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.LongStream;

@SpringBootTest
@Log4j2
public class PollRepositoryTest {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private PollService pollService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PollOptionRepository pollOptionRepository;

    @Test
    void createPollWithPostAndOptions() {
        // Post 객체 생성 및 저장
/*        LongStream.rangeClosed(1L,4L).forEach(i -> {
            Poll poll = Poll.builder()
                    .

                    .build();*/

        Poll poll = Poll.builder()
                .title("pollTitleTest")
                .id(3L)
                .pollCategory("음식")
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .createdBy("Kim")
                             // 참조된 postId 를 넣어야함
                .build();

        Poll savedPoll = pollService.createPoll(poll);

        Optional<Poll> foundPoll = pollRepository.findById(savedPoll.getId());
        assertThat(foundPoll).isPresent();
        assertThat(foundPoll.get().getTitle()).isEqualTo("pollTitleTest");





        }

    }


    //void delete

