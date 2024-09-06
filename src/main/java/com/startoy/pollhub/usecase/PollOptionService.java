package com.startoy.pollhub.usecase;

import com.startoy.pollhub.adapter.repository.PollRepository;
import com.startoy.pollhub.domain.Poll;
import com.startoy.pollhub.domain.PollOption;
import com.startoy.pollhub.adapter.repository.PollOptionRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log4j2
public class PollOptionService {

    private final PollOptionRepository pollOptionRepository;
    private final PollRepository pollRepository;


    public List<PollOption> getOptionsByPollId(Long pollId) {
        // 특정 투표에 대한 모든 선택지 조회
        return pollOptionRepository.findByPollId(pollId);
    }



    public List<PollOption> findAllOptions() {
        return pollOptionRepository.findAll();
    }

    public Optional<PollOption> getOptionById(Long id) {
        return pollOptionRepository.findById(id);
    }

    
    // 투표 옵션 생성
    @Transactional
    public PollOption createOption(PollOption option) {
        try {
            log.info(option);
            // Poll의 존재 확인
            //log.info("getpoll 의 값 : " + option.getPoll()); //
            //log.info("getpoll.getId 의 값 : " + option.getPoll().getId());
            if (option.getPoll() == null || option.getPoll().getId() == null) {
                log.error("PollOption 생성 실패: 연관된 Poll이 없습니다.");
                throw new IllegalArgumentException("Poll을 참조해야 합니다.");
            }

            // Poll이 존재하는지 확인하고 연관 설정
            Poll poll = pollRepository.findById(option.getPoll().getId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Poll입니다."));

            option.setPoll(poll); // PollOption에 Poll 설정

            // PollOption 저장
            return pollOptionRepository.save(option);

        } catch (DataIntegrityViolationException e) {
            // 데이터 무결성 위반 예외 처리
            log.error("데이터 무결성 위반 오류 발생", e);
            throw new RuntimeException("에러 메시지 : 데이터 무결성 위반 오류가 발생했습니다.", e);
        } catch (Exception e) {
            // 기타 예외 처리
            log.error("투표 옵션 생성 중 알 수 없는 오류 발생", e);
            throw new RuntimeException("에러 메시지 : 투표 옵션 생성 중 오류가 발생했습니다.", e);
        }
    }



    // 투표 옵션 수정
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
