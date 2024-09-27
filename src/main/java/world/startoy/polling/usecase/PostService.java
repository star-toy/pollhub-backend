package world.startoy.polling.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.startoy.polling.adapter.repository.PollOptionRepository;
import world.startoy.polling.adapter.repository.PollRepository;
import world.startoy.polling.adapter.repository.PostRepository;
import world.startoy.polling.domain.Poll;
import world.startoy.polling.domain.PollOption;
import world.startoy.polling.domain.Post;
import world.startoy.polling.usecase.dto.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class PostService {

    private final PostRepository postRepository;
    private final PollRepository pollRepository;
    private final PollOptionRepository pollOptionRepository;
    private final PollService pollService;
    private final VoteService voteService;


    // 게시글 전체 가져오기
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    // 모든 게시글 조회 (홈화면 보여질 부분)
    public PostListResponse getPostListResponse(List<Post> postsList) {
        List<PostDTO> postDTOList = postsList.stream()
                .map(post -> PostDTO.builder()
                        .postUid(post.getPostUid())
                        .title(post.getTitle())
                        .fileUid(post.getFile() != null ? post.getFile().getFileUid() : null)
                        .fileFullName(post.getFile() != null ? post.getFile().getFileFullName() : null)
                        .createdBy(post.getCreatedBy())
                        .createdAt(post.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
        return new PostListResponse(postDTOList);
    }


    public Optional<Post> findPostById (Long postId){
        // 게시글 정보 조회
        return postRepository.findById(postId)
                .map(post -> {
                    // 해당 게시글에 연결된 모든 투표를 조회하고, 각 투표의 선택지도 함께 조회
                    List<Poll> polls = pollService.getPollsByPostId(postId);
                    post.setPolls(polls);
                    return post;
                });
    }


    public Optional<Post> findByPostUid (String postUid){
        return postRepository.findByPostUid(postUid);
    }

    public PostDetailResponse getPostDetail(Post post) {
        return createPostDetailResponse(post);
    }

    private PostDetailResponse createPostDetailResponse(Post post) {
        return PostDetailResponse.builder()
                .postUid(post.getPostUid())
                .title(post.getTitle())
                .createdAt(post.getCreatedAt())
                .createdBy(post.getCreatedBy())
                .polls(convertToPollDetailResponses(post.getPolls()))
                .build();
    }

    private List<PollDetailResponse> convertToPollDetailResponses(List<Poll> polls) {
        return polls.stream()
                .map(this::getPollDetailResponse)  // 각 Poll을 PollDetailResponse로 변환
                .collect(Collectors.toList());  // 변환된 PollDetailResponse 리스트로 수집
    }

    private PollDetailResponse getPollDetailResponse(Poll poll) {
        // pollId로부터 득표 정보를 가져오는 부분 추가
        List<PollOptionResponse> pollOptionResponses = voteService.getVoteCountByPollId(poll.getId());

        return PollDetailResponse.builder()
                .pollUid(poll.getPollUid())
                .pollSeq(poll.getPollSeq())
                .pollCategory(poll.getPollCategory())
                .pollDescription(poll.getPollDescription())
                .pollOptions(pollOptionResponses)  // PollOptionResponse 사용
                .build();
    }

    private List<PollOptionDTO> convertToPollOptionDTOs (List < PollOption > options) {
        return options.stream()
                .map(this::getPollOptionDTO) // 각 PollOption을 PollOptionDTO로 변환
                .collect(Collectors.toList()); // 변환된 PollOptionDTO 리스트로 수집
    }


    private PollOptionDTO getPollOptionDTO (PollOption option){
        return PollOptionDTO.builder()
                .pollOptionUid(option.getPollOptionUid())
                .pollOptionSeq(option.getPollOptionSeq())
                .pollOptionText(option.getPollOptionText())
                .build();
    }


    // 새로운 게시글을 생성
    // 하나라도 실패할 경우 전체 작업을 롤백하기 위해 @Transational 사용
    @Transactional
    public Post createPost (Post post){
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


    public PostCreateResponse createPost(PostCreateRequest request, String createdBy) {
        String newPostUid = UUID.randomUUID().toString();
        Post post = Post.builder()
                .postUid(newPostUid)
                .title(request.getTitle())
                .createdAt(LocalDateTime.now())
                .createdBy(createdBy)
                .isDeleted(false) // 기본값 설정
                .build();

        Post savedPost = postRepository.save(post);

        for (PollCreateRequest pollReq : request.getPolls()) {
            String newPollUid = UUID.randomUUID().toString();
            Poll poll = Poll.builder()
                    .pollUid(newPollUid)
                    .pollCategory(pollReq.getPollCategory())
                    .pollDescription(pollReq.getPollDescription())
                    .pollSeq(pollReq.getPollSeq())
                    .post(savedPost) // 관계 설정
                    .isDeleted(false) // 기본값 설정
                    .createdAt(LocalDateTime.now())
                    .createdBy(createdBy)
                    .build();

            Poll savedPoll = pollRepository.save(poll);

            for (PollOptionCreateRequest optionReq : pollReq.getPollOptions()) {
                String newPollOptionUid = UUID.randomUUID().toString();

                PollOption pollOption = PollOption.builder()
                        .pollOptionUid(newPollOptionUid)
                        .poll(savedPoll) // 관계 설정
                        .pollOptionText(optionReq.getPollOptionText())
                        .pollOptionSeq(optionReq.getPollOptionSeq())
                        .isDeleted(false) // 기본값 설정
                        .createdAt(LocalDateTime.now())
                        .createdBy(createdBy)
                        .build();

                pollOptionRepository.save(pollOption);
            }
        }

        return PostCreateResponse.builder()
                .postUid(savedPost.getPostUid())
                .build();
    }

}