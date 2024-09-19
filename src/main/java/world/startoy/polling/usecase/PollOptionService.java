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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log4j2
public class PollOptionService {

    private final PollOptionRepository pollOptionRepository;
    private final PollRepository pollRepository;


    public List<PollOption> findOptionsByPollId(Long pollId) {
        // 특정 투표에 대한 모든 선택지 조회
        return pollOptionRepository.findByPollId(pollId);
    }


    // 특정 ID의 투표 옵션 조회
    public PollOption findOptionById(Long optionId) {
        Optional<PollOption> optionalPollOption = pollOptionRepository.findById(optionId);

        if (optionalPollOption.isPresent()) {
            return optionalPollOption.get();

        } else {
            log.error("투표 옵션 조회 중 오류 발생: 해당 ID의 투표 옵션을 찾을 수 없음.");
            throw new EntityNotFoundException("Poll option with ID " + optionId + " not found.");
        }
    }

    
    // 투표 옵션 생성
    @Transactional
    public PollOption createOption(PollOption option) {
        try {
            log.info(option);
            // Poll의 존재 확인
            if (option.getPoll() == null || option.getPoll().getId() == null) {
                log.error("PollOption 생성 실패: 연관된 Poll이 없음.");
                throw new IllegalArgumentException("Poll을 참조해야 함.");
            }

            // Poll이 존재하는지 확인하고 연관 설정
            Poll poll = pollRepository.findById(option.getPoll().getId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Poll임."));

            option.setPoll(poll); // PollOption에 Poll 설정

            // PollOption 저장
            return pollOptionRepository.save(option);

        } catch (DataIntegrityViolationException e) {
            // 데이터 무결성 위반 예외 처리
            log.error("데이터 무결성 위반 오류 발생", e);
            throw new RuntimeException("에러 메시지 : 데이터 무결성 위반 오류가 발생.", e);
        } catch (Exception e) {
            // 기타 예외 처리
            log.error("투표 옵션 생성 중 알 수 없는 오류 발생", e);
            throw new RuntimeException("에러 메시지 : 투표 옵션 생성 중 오류가 발생.", e);
        }
    }


    // 특정 ID의 투표 옵션 수정
    @Transactional
    public PollOption updateOption(Long optionId, PollOption option) {

        // PostService.java 의 게시글 수정 부분과 비교시 장단점이 분명해서 코드 통일 여부 고민중
        return pollOptionRepository.findById(optionId).map(existingOption -> {

            existingOption.setOptionText(option.getOptionText());
            existingOption.setVotedCount(option.getVotedCount());
            existingOption.setFileId(option.getFileId());
            existingOption.setIsDeleted(option.getIsDeleted());
            existingOption.setUpdatedAt(LocalDateTime.now());
            existingOption.setUpdatedBy(option.getUpdatedBy());

            return pollOptionRepository.save(existingOption);
        }).orElseThrow(() -> new EntityNotFoundException("Poll option with ID " + optionId + " not found."));
    }


    // 특정 ID의 투표 옵션 삭제
    public void deleteOption(Long optionId) {
        try {
            if (pollOptionRepository.existsById(optionId)) {
                pollOptionRepository.deleteById(optionId);

            } else {
                log.error("투표 옵션 삭제 중 오류 발생: 해당 ID의 게시글을 찾을 수 없음.");
                throw new EntityNotFoundException("Poll option with ID " + optionId + " not found.");
            }
        } catch (EntityNotFoundException e) {
            log.error("투표 옵션 삭제 중 오류 발생: 해당 ID의 옵션을 찾을 수 없음. ID: {}", optionId);
            throw e;

        } catch (Exception e) {
            log.error("투표 옵션 삭제 중 알 수 없는 오류 발생: {}", e.getMessage());
            throw new RuntimeException("투표 옵션 삭제 중 오류가 발생.", e);
        }
    }
}
