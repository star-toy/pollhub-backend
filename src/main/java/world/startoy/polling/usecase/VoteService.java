package world.startoy.polling.usecase;

import world.startoy.polling.adapter.repository.PollOptionRepository;
import world.startoy.polling.adapter.repository.VoteRepository;
import world.startoy.polling.domain.Vote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import world.startoy.polling.usecase.dto.OptionVoteRateDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.*;


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


    // 투표율 조회
    public List<OptionVoteRateDTO> getVoteRates(Long pollId) {
        // 특정 pollId에 대한 모든 투표 가져오기
        List<Vote> votes = voteRepository.findByPollId(pollId);

        // 전체 투표 수 계산
        long totalVotes = votes.size();

        // 각 optionId별 투표 수 집계
        /*
         * Java Stream API 를 이용 Vote 엔티티 리스트를 optionId를 기준으로 그룹화하고
         * 각 그룹의 투표 수를 계산하여 Map 형태로 반환
         * */
        Map<Long, Long> votesPerOption = votes.stream()
                .collect(Collectors.groupingBy(Vote::getOptionId, Collectors.counting()));

        // 각 optionId에 해당하는 OptionVoteRate 생성
        return votesPerOption.entrySet().stream()
                .map(entry -> {
                    // PollOption 데이터에서 UID와 텍스트 가져오기
                    Long optionId = entry.getKey(); // 백엔드에서 사용되는 PollOption의 ID
                    var pollOption = pollOptionRepository.findById(optionId)
                            .orElseThrow(() -> new IllegalStateException("옵션 ID를 찾을 수 없습니다: " + optionId));

                    return new OptionVoteRateDTO(
                            pollOption.getPollOptionUid(),                // API 응답에 사용될 PollOptionUid
                            pollOption.getPollOptionText(),               // optionText
                            entry.getValue(),                             // votes
                            totalVotes == 0 ? 0 : (entry.getValue() * 100.0 / totalVotes) // voteRate
                    );
                })
                .collect(Collectors.toList());
    }


}