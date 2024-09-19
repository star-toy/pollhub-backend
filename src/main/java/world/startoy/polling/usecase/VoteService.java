package world.startoy.polling.usecase;

import world.startoy.polling.adapter.repository.VoteRepository;
import world.startoy.polling.domain.Vote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class VoteService {

    private final VoteRepository voteRepository;


    // 투표
    public Vote submitVote(Long pollId, Long optionId, String voterIp) {
        if (hasVoted(pollId, voterIp)) {
            throw new IllegalStateException("이미 투표하셨습니다.");
        }

        String voteUid = UUID.randomUUID().toString();

        Vote vote = new Vote();
        vote.setVoteUid(voteUid);
        vote.setPollId(pollId);
        vote.setOptionId(optionId);
        vote.setVoterIp(voterIp);
        return voteRepository.save(vote);
    }


    // 투표 여부 확인
    public boolean hasVoted(Long pollId, String voterIp) {
        return !voteRepository.findByPollIdAndVoterIp(pollId, voterIp).isEmpty();
    }


}
