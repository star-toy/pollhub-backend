package world.startoy.polling.usecase;

import world.startoy.polling.adapter.repository.PollOptionRepository;
import world.startoy.polling.adapter.repository.PollRepository;
import world.startoy.polling.domain.Poll;
import world.startoy.polling.domain.PollOption;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor  // final 필드만 포함된 생성자 생성
@Service
@Log4j2
public class PollService {

    private final PollRepository pollRepository;
    private final PollOptionRepository pollOptionRepository;
    private final PollOptionService pollOptionService;
    // private final @Lazy PostService postService; // 순환 의존성 때문에 PostService 필요 시 지연 주입


    public List<Poll> getPollsByPostId(Long postId) {
        // 특정 게시글에 대한 모든 투표 조회
        List<Poll> polls = pollRepository.findByPostId(postId);

        // 각 투표에 대해 선택지 목록을 조회하여 설정
        for (Poll poll : polls) {
            List<PollOption> options = pollOptionService.findOptionsByPollId(poll.getId());
            poll.setOptions(options); // 조회된 선택지 목록을 투표에 설정
        }

        return polls; // 투표 및 선택지 목록을 포함하여 반환
    }

    // 투표 전체 목록 조회
    public List<Poll> findAllPolls() { // 데이터가 많아지면 페이징 처리 할 예정
        return pollRepository.findAll();
    }

    // 새로운 투표 생성
    @Transactional
    public Poll createPoll(Poll poll) {
        try {

            // PollOption 이 없을 때
            if (poll.getOptions() != null && !poll.getOptions().isEmpty()) {
                log.warn("생성된 투표에 투표 옵션이 없음.");
                return pollRepository.save(poll); // Poll만 저장하고 반환
            }

            // poll 저장
            Poll savedPoll = pollRepository.save(poll);

            // poll option 저장
            for (PollOption option : poll.getOptions()) {
                option.setPoll(savedPoll); // Poll과 연결 설정
                pollOptionRepository.save(option); // PollOption 저장
            }
            return savedPoll;

        } catch (DataIntegrityViolationException e) {
            // 데이터 무결성 위반 예외 처리
            log.error("데이터 무결성 위반 오류 발생. {}", e.getMessage());
            throw new RuntimeException("에러 메시지 : 데이터 무결성 위반 오류 발생", e);
        } catch (Exception e) {
            // 기타 예외 처리
            log.error("투표 생성 중 알 수 없는 오류 발생 {}", e.getMessage());
            throw new RuntimeException("에러 메시지 : 투표 생성 중 오류가 발생.", e);
        }

    }


    // 투표 수정
    @Transactional
    public Poll updatePoll(Long pollId, Poll poll) {
        Optional<Poll> existingPoll = pollRepository.findById(pollId);

        if (existingPoll.isPresent()) {
            Poll toUpdate = existingPoll.get();

            if (poll.getPollDescription() != null && !poll.getPollDescription().equals(toUpdate.getPollDescription())) {
                toUpdate.setPollDescription(poll.getPollDescription());
            }

            List<PollOption> existingOptions = toUpdate.getOptions();
            List<PollOption> newOptions = poll.getOptions();

            // 기존 옵션과 새로운 옵션을 비교하여 업데이트
            // 기존 옵션이 있다면 업데이트, 없다면 추가
            for (PollOption newOption : newOptions) {
                boolean optionExists = existingOptions.stream() //
                        .anyMatch(option -> option.getId().equals(newOption.getId()));

                if (optionExists) {
                    existingOptions.stream()
                            .filter(option -> option.getId().equals(newOption.getId()))
                            .forEach(option -> {
                                option.setPollOptionText(newOption.getPollOptionText());
                                // 필요에 따라 추가 필드 업데이트
                            });
                } else {
                    newOption.setPoll(toUpdate);
                    existingOptions.add(newOption);
                }
            }

            // 기존 옵션 중에서 새 옵션에 없는 것 삭제
            existingOptions.removeIf(option ->
                    newOptions.stream().noneMatch(newOption -> newOption.getId().equals(option.getId()))
            );

            return pollRepository.save(toUpdate);
        } else {
            throw new EntityNotFoundException("Poll with ID " + pollId + " not found.");
        }
    }

    // 투표 삭제
    @Transactional
    public void deletePoll(Long pollId) {
        try {

            Optional<Poll> existingPoll = pollRepository.findById(pollId);

            if (existingPoll.isPresent()) {

                // 투표 삭제 시 관련된 PollOption도 함께 삭제됨
                pollRepository.delete(existingPoll.get());
            } else {
                throw new EntityNotFoundException("Poll with ID " + pollId + " not found.");
            }
        } catch (EntityNotFoundException e) {
            log.error("투표 삭제 중 오류 발생: 해당 ID의 투표를 찾을 수 없음. {}", e.getMessage());
            throw e;

        } catch (Exception e) {
            log.error("투표 삭제 중 예기치 못한 오류가 발생: {}", e.getMessage());
            throw new RuntimeException("투표 삭제 중 오류가 발생.", e);
        }
    }
}
