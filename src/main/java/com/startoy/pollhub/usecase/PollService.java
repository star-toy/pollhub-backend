package com.startoy.pollhub.usecase;

import com.startoy.pollhub.domain.Poll;
import com.startoy.pollhub.adapter.repository.PollRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    private final PollRepository pollRepository;

    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    public Optional<Poll> getPollById(Long id) {
        return pollRepository.findById(id);
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
