package world.startoy.polling.usecase;

import world.startoy.polling.adapter.repository.PollOptionRepository;
import world.startoy.polling.adapter.repository.PollRepository;
import world.startoy.polling.adapter.repository.PostRepository;
import world.startoy.polling.domain.Poll;
import world.startoy.polling.domain.PollOption;
import world.startoy.polling.domain.Post;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.startoy.polling.usecase.dto.PollDTO;
import world.startoy.polling.usecase.dto.PollOptionDTO;
import world.startoy.polling.usecase.dto.PostDetailResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
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

    public Optional<Post> findByPostUid(String postUid) {
        return postRepository.findByPostUid(postUid);
    }

    public PostDetailResponse getPostDetail(Post post) {
        return createPostDetailResponse(post);
    }

    private PostDetailResponse createPostDetailResponse(Post post) {
        PostDetailResponse response = new PostDetailResponse();
        response.setPostUid(post.getPostUid());
        response.setTitle(post.getTitle());
        response.setCreatedAt(post.getCreatedAt());
        response.setCreatedBy(post.getCreatedBy());
        response.setPolls(convertToPollDTOs(post.getPolls()));
        return response;
    }

    private List<PollDTO> convertToPollDTOs(List<Poll> polls) {
        List<PollDTO> pollDTOs = new ArrayList<>();

        for (Poll poll : polls) {
            pollDTOs.add(getPollDTO(poll));
        }
        return pollDTOs;
    }

    private PollDTO getPollDTO(Poll poll) {
        List<PollOptionDTO> pollOptionDTOs = convertToPollOptionDTOs(poll.getOptions());

        return new PollDTO(
                poll.getPollUid(),
                poll.getPollSeq(),
                poll.getPollCategory(),
                poll.getPollDescription(),
                pollOptionDTOs);
    }

    private List<PollOptionDTO> convertToPollOptionDTOs(List<PollOption> options) {
        List<PollOptionDTO> pollOptionDTOs = new ArrayList<>();
        for (PollOption option : options) {
            pollOptionDTOs.add(getPollOptionDTO(option));
        }
        return pollOptionDTOs;
    }

    private PollOptionDTO getPollOptionDTO(PollOption option) {
        return new PollOptionDTO(
                option.getPollOptionUid(),
                option.getPollOptionSeq(),
                option.getPollOptionText());
    }

    // 새로운 게시글을 생성
    // 하나라도 실패할 경우 전체 작업을 롤백하기 위해 @Transational 사용
    @Transactional
    public Post createPost(Post post) {
        Post savedPost;

        try {
            // Post 저장
            savedPost = postRepository.save(post);

        } catch (DataIntegrityViolationException e) {
            // 데이터 무결성 위반 예외 처리
            log.error("Post 저장 중 데이터 무결성 위반 오류 발생: {}", e.getMessage());
            throw new RuntimeException("에러 메시지 : Post 저장 중 데이터 무결성 위반 오류가 발생.", e);

        } catch (Exception e) {
            log.error("Post 저장 중 알 수 없는 오류 발생: {}", e.getMessage());
            throw new RuntimeException("에러 메시지 : Post 저장 중 오류가 발생.", e);

        }

        // Poll과 PollOption 간의 참조 설정
        for (Poll poll : savedPost.getPolls()) {
            Poll savedPoll;

            try {
                // Poll 저장
                poll.setPost(savedPost);
                savedPoll = pollRepository.save(poll);

            } catch (DataIntegrityViolationException e) {
                log.error("Poll 저장 중 데이터 무결성 위반 오류 발생: {}", e.getMessage());
                throw new RuntimeException("에러 메시지 : Poll 저장 중 데이터 무결성 위반 오류 발생", e);

            } catch (Exception e) {
                log.error("Poll 저장 중 알 수 없는 오류 발생: {}", e.getMessage());
                throw new RuntimeException("에러 메시지 : Poll 저장 중 오류 발생", e);

            }

            for (PollOption option : poll.getOptions()) {
                try {
                    // PollOption 저장
                    option.setPoll(savedPoll);
                    pollOptionRepository.save(option);

                } catch (DataIntegrityViolationException e) {
                    log.error("PollOption 저장 중 데이터 무결성 위반 오류 발생: {}", e.getMessage());
                    throw new RuntimeException("에러 메시지 : PollOption 저장 중 데이터 무결성 위반 오류 발생", e);

                } catch (Exception e) {
                    log.error("PollOption 저장 중 알 수 없는 오류 발생: {}", e.getMessage());
                    throw new RuntimeException("에러 메시지 : PollOption 저장 중 오류 발생", e);

                }
            }
        }

        return savedPost;
    }

}
