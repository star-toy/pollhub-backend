package com.startoy.pollhub.repository;


import com.startoy.pollhub.adapter.repository.PollOptionRepository;
import com.startoy.pollhub.domain.PollOption;
import com.startoy.pollhub.usecase.PollOptionService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class PollOptionRepositoryTest {

    private PollOptionRepository pollOptionRepository;

    private PollOptionService pollOptionService;

    @Test
    void createPollOption() {
        Long option_id = 1L;
        Long poll_id = 1L;

        //PollOption pollOption = new PollOption(option_id, poll_id, 1,"테스트",1L,false,);


    }


}
