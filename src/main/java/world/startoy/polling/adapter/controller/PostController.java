package world.startoy.polling.adapter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import world.startoy.polling.usecase.PostService;
import world.startoy.polling.usecase.UserService;
import world.startoy.polling.usecase.dto.PostCreateRequest;
import world.startoy.polling.usecase.dto.PostCreateResponse;
import world.startoy.polling.usecase.dto.PostDetailResponse;
import world.startoy.polling.usecase.dto.PostListResponse;

@RequiredArgsConstructor
@RestController
@Log4j2
@RequestMapping("/v1/posts")
@Tag(name = "Post", description = "Post API")
public class PostController {

    private final PostService postService;
    private final UserService userService;


    @GetMapping
    @Operation(summary = "게시글 전체 조회 - 메인페이지 게시글 목록")
    public ResponseEntity<PostListResponse> findAllPostsList() {
        PostListResponse postListResponse = postService.getPostListResponse();
        return ResponseEntity.ok(postListResponse);
    }


    @GetMapping("/{postUid}")
    @Operation(summary = "특정 UID의 게시글 상세 조회")
    public ResponseEntity<PostDetailResponse> getPostByUid(
            @PathVariable String postUid,
            HttpServletRequest httpRequest) {
        String userIp = userService.getClientIp(httpRequest);
        return postService.findPostDetailResponseByPostUid(postUid, userIp) // Optional<PostDetailResponse> 처리
                .map(ResponseEntity::ok)  // PostDetailResponse가 존재할 경우 200 OK 반환
                .orElse(ResponseEntity.notFound().build()); // 없으면 404 Not Found 반환
    }


    @PostMapping
    @Operation(summary = "새로운 게시글 생성")
    public ResponseEntity<PostCreateResponse> createPost(
            @Valid @RequestBody PostCreateRequest request,
            HttpServletRequest httpRequest) {
        String uploaderIp = userService.getClientIp(httpRequest);
        PostCreateResponse response = postService.createPost(request, uploaderIp);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
