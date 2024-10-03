package world.startoy.polling.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.startoy.polling.adapter.repository.FileStorageRepository;
import world.startoy.polling.adapter.repository.PollOptionRepository;
import world.startoy.polling.adapter.repository.PollRepository;
import world.startoy.polling.adapter.repository.PostRepository;
import world.startoy.polling.config.CloudFrontConfig;
import world.startoy.polling.domain.FileStorage;
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
    private final FileStorageRepository fileStorageRepository;
    private final CloudFrontConfig cloudFrontConfig;


    // 모든 게시글 조회 (메인페이지 보여질 부분)
    public PostListResponse getPostListResponse() {
        String cloudFrontUrl = cloudFrontConfig.getCloudfrontUrl();
        List<PostDTO> postDTOList = postRepository.findAllPostWithFile(cloudFrontUrl);

        return new PostListResponse(postDTOList);
    }


    // 게시글 상세 조회하기
    public Optional<PostDetailResponse> getPostDetail(String postUid) {
        return postRepository.findByPostUid(postUid)
                .map(this::createPostDetailResponse);
    }

    private PostDetailResponse createPostDetailResponse(Post post) {
        String cloudFrontUrl = cloudFrontConfig.getCloudfrontUrl();
        String imageUrl = post.getFile() != null ? cloudFrontUrl + "/" + post.getFile().getFileName() : null;

        return PostDetailResponse.builder()
                .postUid(post.getPostUid())
                .title(post.getTitle())
                .polls(convertToPollDetailResponses(post.getPolls()))
                .imageUrl(imageUrl)
                .createdBy(post.getCreatedBy())
                .createdAt(post.getCreatedAt())
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


    @Transactional
    public PostCreateResponse createPost(PostCreateRequest request, String createdBy) {
        String newPostUid = UUID.randomUUID().toString();

        FileStorage postFile = FileStorage.builder()
                .fileUid(request.getFileUid())
                .fileName(request.getFileName())
                .isDeleted(false) // 기본값 설정
                .createdAt(LocalDateTime.now())
                .createdBy(createdBy)
                .fileLinkedUid(newPostUid)
                .uploadableType("Post")
                .build();

        FileStorage savedPostFile = fileStorageRepository.save(postFile);

        Post post = Post.builder()
                .postUid(newPostUid)
                .title(request.getTitle())
                .createdAt(LocalDateTime.now())
                .createdBy(createdBy)
                .isDeleted(false) // 기본값 설정
                .file(savedPostFile)
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

                FileStorage pollOptionFile = FileStorage.builder()
                        .fileUid(optionReq.getFileUid())
                        .fileName(optionReq.getFileName())
                        .isDeleted(false) // 기본값 설정
                        .createdAt(LocalDateTime.now())
                        .createdBy(createdBy)
                        .fileLinkedUid(newPostUid)
                        .uploadableType("PollOption")
                        .build();

                FileStorage savedPollOptionFile = fileStorageRepository.save(pollOptionFile);

                PollOption pollOption = PollOption.builder()
                        .pollOptionUid(newPollOptionUid)
                        .poll(savedPoll) // 관계 설정
                        .pollOptionText(optionReq.getPollOptionText())
                        .pollOptionSeq(optionReq.getPollOptionSeq())
                        .isDeleted(false) // 기본값 설정
                        .createdAt(LocalDateTime.now())
                        .createdBy(createdBy)
                        .file(savedPollOptionFile)
                        .build();

                pollOptionRepository.save(pollOption);
            }
        }

        return PostCreateResponse.builder()
                .postUid(savedPost.getPostUid())
                .build();
    }
}