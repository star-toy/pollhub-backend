package com.startoy.pollhub.usecase;

import com.startoy.pollhub.adapter.repository.PollOptionRepository;
import com.startoy.pollhub.adapter.repository.PollRepository;
import com.startoy.pollhub.domain.Poll;
import com.startoy.pollhub.domain.PollOption;
import com.startoy.pollhub.domain.Post;
import com.startoy.pollhub.adapter.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Log4j2
public class PostService {


    private final PostRepository postRepository;
    private final PollService pollService;
    private final PollRepository pollRepository;
    private final PollOptionRepository pollOptionRepository;


    // 게시글 전체 가져오기
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }


    public Optional<Post> findPostById(Long postId) {
        // 게시글 정보 조회
        Post post = postRepository.findById(postId).orElse(null);

        if (post == null) {
            return null;
        }

        // 해당 게시글에 연결된 모든 투표를 조회하고, 각 투표의 선택지도 함께 조회
        List<Poll> polls = pollService.getPollsByPostId(postId);
        post.setPolls(polls);

        return Optional.of(post);
    }

    // 새로운 게시글을 생성
    // 하나라도 실패할 경우 전체 작업을 롤백하기 위해 @Transational 사용
    @Transactional
    public Post createPost(Post post) {
        try {
            // Post 저장
            Post savedPost = postRepository.save(post);

            // Poll과 PollOption 간의 참조 설정
            for (Poll poll : savedPost.getPolls()) {
                poll.setPost(savedPost); // Poll이 Post를 참조하도록 설정
                Poll savedPoll = pollRepository.save(poll);

                for (PollOption option : poll.getOptions()) {
                    option.setPoll(savedPoll); // PollOption이 Poll을 참조하도록 설정
                    pollOptionRepository.save(option); // PollOption 저장
                }
            }

            return savedPost;

        } catch (DataIntegrityViolationException e) {
            // 데이터 무결성 위반 예외 처리
            log.error("데이터 무결성 위반 오류 발생: {}", e.getMessage());
            throw new RuntimeException("에러 메시지 : 데이터 무결성 위반 오류가 발생.", e);

        } catch (Exception e) {
            log.error("게시글 저장 중 알 수 없는 오류 발생: {}", e.getMessage());
            throw new RuntimeException("에러 메시지 : 게시글 저장 중 오류가 발생.", e);

        }
    }


    // 특정 ID의 게시글을 수정
    @Transactional
    public Post updatePost(Long postId, Post post) {
        try {
            Optional<Post> existingPost = postRepository.findById(postId);

            if (existingPost.isPresent()) {
                Post toUpdatePost = existingPost.get();

                // 제목 업데이트
                if (post.getTitle() != null && !post.getTitle().equals(toUpdatePost.getTitle())) {
                    toUpdatePost.setTitle(post.getTitle());
                }

                // 파일 ID 업데이트
                if (post.getFileId() != null && !post.getFileId().equals(toUpdatePost.getFileId())) {
                    toUpdatePost.setFileId(post.getFileId());
                }

                // 삭제 상태 업데이트
                if (post.getIsDeleted() != null && !post.getIsDeleted().equals(toUpdatePost.getIsDeleted())) {
                    toUpdatePost.setIsDeleted(post.getIsDeleted());
                }

                // 업데이트 시간 설정
                toUpdatePost.setUpdatedAt(LocalDateTime.now());

                // 업데이트 사용자 설정
                if (post.getUpdatedBy() != null && !post.getUpdatedBy().isBlank()) {
                    toUpdatePost.setUpdatedBy(post.getUpdatedBy());
                }

                return postRepository.save(toUpdatePost);

            } else {
                log.error("게시글 수정 중 오류 발생: 해당 ID의 게시글을 찾을 수 없음.");
                throw new EntityNotFoundException("Post with ID " + postId + " not found.");
            }
        } catch (EntityNotFoundException e) {
            log.error("EntityNotFoundException 발생: " + e.getMessage());
            throw e;

        } catch (Exception e) {
            log.error("게시글 수정 중 알 수 없는 오류 발생", e);
            throw new RuntimeException("게시글 수정 중 오류가 발생.", e);
        }
    }


    // 특정 ID의 게시글을 삭제
    @Transactional
    public void deletePost(Long postId) {
        try {
            Optional<Post> existingPost = postRepository.findById(postId);
            if (existingPost.isPresent()) {
                postRepository.delete(existingPost.get());
            } else {

                throw new EntityNotFoundException("Post with ID " + postId + " not found.");
            }
        } catch (EntityNotFoundException e) {
            log.error("게시글 수정 중 오류 발생: 해당 ID의 게시글을 찾을 수 없음. {}", e.getMessage());
            throw e;

        } catch (Exception e) {
            log.error("게시글 삭제 중 오류가 발생: {}", e.getMessage());
            throw new RuntimeException("게시글 삭제 중 오류가 발생.", e);
        }
    }

}
