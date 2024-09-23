package world.startoy.polling.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import world.startoy.polling.adapter.repository.PollOptionRepository;
import world.startoy.polling.adapter.repository.VoteRepository;


@RequiredArgsConstructor
@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final PollOptionRepository pollOptionRepository; // PollOption 데이터를 가져오기 위한 Repository




}
