package com.startoy.pollhub.usecase;

import com.startoy.pollhub.adapter.repository.PollOptionRepository;
import com.startoy.pollhub.domain.Poll;
import com.startoy.pollhub.adapter.repository.PollRepository;
import com.startoy.pollhub.domain.PollOption;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Log4j2
public class PollService {


    private final PollRepository pollRepository;
    private final PollOptionRepository pollOptionRepository;
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

    public List<Poll> findAllPolls() {
        return pollRepository.findAll();
    }

    // 새로운 투표 생성
    //
    @Transactional
    public Poll createPoll(Poll poll) {
        try {
            // Poll 저장
            Poll savedPoll = pollRepository.save(poll);


            // PollOption 저장
            if (poll.getOptions() != null && !poll.getOptions().isEmpty()) {
                for (PollOption option : poll.getOptions()) {
                    option.setPoll(savedPoll); // Poll과 연결 설정
                    pollOptionRepository.save(option); // PollOption 저장
                }
            } else {
                // PollOption이 하나도 없을 때 예외 처리 또는 기본 값 설정
                log.warn("생성된 투표에 투표 옵션이 없습니다.");
            }

            // 3. 저장된 Poll 반환
            return savedPoll;

        } catch (DataIntegrityViolationException e) {
            // 데이터 무결성 위반 예외 처리
            log.error("has Errors..... 데이터 무결성 위반 오류 발생", e);
            throw new RuntimeException("에러 메시지 : 데이터 무결성 위반 오류가 발생했습니다.", e);
        } catch (Exception e) {
            // 기타 예외 처리
            log.error("has Errors..... 투표 생성 중 알 수 없는 오류 발생", e);
            throw new RuntimeException("에러 메시지 : 투표 생성 중 오류가 발생했습니다.", e);
        }

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
