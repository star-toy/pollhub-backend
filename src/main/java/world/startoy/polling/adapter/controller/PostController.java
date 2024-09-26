package world.startoy.polling.adapter.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import world.startoy.polling.domain.Post;
import world.startoy.polling.usecase.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import world.startoy.polling.usecase.UserService;
import world.startoy.polling.usecase.dto.*;

import java.util.*;

@RequiredArgsConstructor
@RestController
@Log4j2
@RequestMapping("/v1/posts")
@Tag(name = "게시글 Post", description = "게시글 관리 API")
public class PostController {

    private final PostService postService;
    private final UserService userService;


    // 모든 게시글 조회
    @GetMapping
    @Operation(summary = "모든 게시글 조회")
    public ResponseEntity<List<Post>> findAllPosts() {
        List<Post> posts = postService.findAllPosts();

        if (posts.isEmpty()) {
            // 게시글이 없을 경우 빈 목록과 함께 200 OK 반환
            return ResponseEntity.ok(Collections.emptyList());
        }

        // 게시글이 있을 경우 게시글 목록과 함께 200 OK 반환
        return ResponseEntity.ok(posts);
    }

    // 게시글 전체 조회 - 메인페이지 게시글 목록
    @GetMapping("/list")
    @Operation(summary = "모든 게시글 조회")
    public ResponseEntity<PostListResponse> findAllPostsList() {
        // 게시글 전체 목록
        List<Post> postsList = postService.findAllPosts();

        // PostListResponse 객체 생성 및 반환
        PostListResponse postListResponse = postService.getPostListResponse(postsList);
        return ResponseEntity.ok(postListResponse);
    }

    
    //  특정 ID의 게시글을 조회
    @GetMapping("/{postId}")
    @Operation(summary = "게시글 상세 조회")
    public ResponseEntity<Optional<Post>> getPostById(@PathVariable Long postId) { // 경로 매개변수를 통해 postId 수신
        Optional<Post> post = postService.findPostById(postId); // PostService를 통해 게시글과 관련된 모든 데이터 조회

        if (post.isEmpty()) { // 게시글이 존재하지 않을 경우 404 Not Found 응답
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(post); // 게시글이 존재할 경우 200 OK와 함께 게시글 데이터 응답
    }


    //  특정 UID의 게시글을 조회
    @GetMapping("uid/{postUid}")
    @Operation(summary = "게시글 상세 조회")
    public ResponseEntity<PostDetailResponse> getPostByUid(@PathVariable String postUid) { // 경로 매개변수를 통해 postUid 수신
        Optional<Post> optionalPost = postService.findByPostUid(postUid); // PostService를 통해 게시글과 관련된 모든 데이터 조회

        if (optionalPost.isEmpty()) { // 게시글이 존재하지 않을 경우 404 Not Found 응답
            return ResponseEntity.notFound().build();
        }

        PostDetailResponse response = postService.getPostDetail(optionalPost.get());

        return ResponseEntity.ok(response); // 게시글이 존재할 경우 200 OK와 함께 게시글 데이터 응답
    }


    // 게시글 등록
    @PostMapping
    @Operation(summary = "새로운 게시글 생성")
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post) {
        // 검증이 성공하면 서비스 호출.@Valid 어노테이션이 사용되어 Post 객체의 유효성을 검증
        // 유효성 검사가 실패하면 400 Bad Request 상태 코드가 자동으로 반환
        Post createdPost = postService.createPost(post);

        return ResponseEntity.status(201).body(createdPost); // 201 : 리소스를 생성하는 요청(예: POST 요청)을 처리할 때 사용
        // return "redirect:/board/register"; 게시글 등록 후 redirect 될 화면 명시 예정
    }



    // 게시글 등록
    @PostMapping("new/")
    @Operation(summary = "새로운 게시글 생성")
    public ResponseEntity<PostCreateResponse> createPost(
            @Valid @RequestBody PostCreateRequest request,
            HttpServletRequest httpRequest) {
        String uploaderIp = userService.getClientIp(httpRequest);
        PostCreateResponse response = postService.createPost(request, uploaderIp);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}

