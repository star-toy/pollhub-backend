package com.startoy.pollhub.adapter.controller;

import com.startoy.pollhub.domain.Post;
import com.startoy.pollhub.usecase.PostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private final PostService postService;

    @GetMapping
    @Operation(summary = "모든 게시글 조회")
    public List<Post> getAllPosts() {
        return postService.findAllPosts();
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


    @PostMapping
    @Operation(summary = "새로운 게시글 생성")
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post) {
        // 검증이 성공하면 서비스 호출.@Valid 어노테이션이 사용되어 Post 객체의 유효성을 검증
        // 유효성 검사가 실패하면 400 Bad Request 상태 코드가 자동으로 반환
        Post createdPost = postService.createPost(post);

        return ResponseEntity.status(201).body(createdPost); // 201 : 리소스를 생성하는 요청(예: POST 요청)을 처리할 때 사용
        // return "redirect:/board/register"; 게시글 등록 후 redirect 될 화면 명시 예정
    }

    // 특정 ID의 게시글을 업데이트
    @PutMapping("/{id}")
    @Operation(summary = "게시글 수정")
    public Post updatePost(@PathVariable Long id, @RequestBody Post post) {
        return postService.updatePost(id, post);
    }


    // 특정 ID의 게시글을 삭제
    @DeleteMapping("/{id}")
    @Operation(summary = "게시글 삭제")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
}
