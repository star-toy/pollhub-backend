package world.startoy.polling.usecase;

import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import world.startoy.polling.adapter.repository.PollOptionRepository;
import world.startoy.polling.adapter.repository.VoteRepository;
import world.startoy.polling.domain.Vote;
import world.startoy.polling.usecase.dto.PollOptionResponse;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final PollOptionRepository pollOptionRepository; // PollOption 데이터를 가져오기 위한 Repository


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


    public List<PollOptionResponse> getVoteCountByPollId(Long pollId) {
        List<Tuple> pollOptions = voteRepository.countVotesByPollId(pollId);
        return pollOptions.stream()
                .map(tuple -> new PollOptionResponse(
                        tuple.get("pollOptionUid", String.class),
                        tuple.get("pollOptionSeq", Integer.class),  // int -> Integer.class로 수정
                        tuple.get("pollOptionText", String.class),
                        tuple.get("votedCount", Long.class).intValue() // Long으로 가져온 후 int로 변환
                ))
                .collect(Collectors.toList());
    }
}
