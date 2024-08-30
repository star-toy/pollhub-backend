package com.startoy.pollhub.usecase;

import com.startoy.pollhub.domain.PollOption;
import com.startoy.pollhub.adapter.repository.PollOptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollOptionService {

    private final PollOptionRepository pollOptionRepository;

    public PollOptionService(PollOptionRepository pollOptionRepository) {
        this.pollOptionRepository = pollOptionRepository;
    }

    public List<PollOption> getAllOptions() {
        return pollOptionRepository.findAll();
    }

    public Optional<PollOption> getOptionById(Long id) {
        return pollOptionRepository.findById(id);
    }

    public PollOption createOption(PollOption option) {
        return pollOptionRepository.save(option);
    }

    public PollOption updateOption(Long id, PollOption option) {
        if (pollOptionRepository.existsById(id)) {
            option.setId(id);
            return pollOptionRepository.save(option);
        }
        return null;
    }

    public void deleteOption(Long id) {
        pollOptionRepository.deleteById(id);
    }
}
