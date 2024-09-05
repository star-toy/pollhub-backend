package com.startoy.pollhub.usecase;

import com.startoy.pollhub.domain.Poll;
import com.startoy.pollhub.adapter.repository.PollRepository;
import com.startoy.pollhub.domain.PollOption;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PollService {

    @Autowired
    private final PollRepository pollRepository;

    @Autowired
    private final PollOptionService pollOptionService;

    public List<Poll> getPollsByPostId(Long postId) {
        // 특정 게시글에 대한 모든 투표 조회
        List<Poll> polls = pollRepository.findByPostId(postId);

        // 각 투표에 대해 선택지 목록을 조회하여 설정
        for (Poll poll : polls) {
            List<PollOption> options = pollOptionService.getOptionsByPollId(poll.getId());
            poll.setOptions(options); // 조회된 선택지 목록을 투표에 설정
        }

        return polls; // 투표 및 선택지 목록을 포함하여 반환
    }

    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    public Poll createPoll(Poll poll) {
        return pollRepository.save(poll);
    }

    public Poll updatePoll(Long id, Poll poll) {
        if (pollRepository.existsById(id)) {
            poll.setId(id);
            return pollRepository.save(poll);
        }
        return null;
    }

    public void deletePoll(Long id) {
        pollRepository.deleteById(id);
    }
}
