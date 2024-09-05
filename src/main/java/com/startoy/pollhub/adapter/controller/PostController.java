package com.startoy.pollhub.adapter.controller;

import com.startoy.pollhub.domain.Post;
import com.startoy.pollhub.usecase.PostService;
import io.swagger.v3.oas.annotations.Operation;
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
        return postService.getAllPosts();
    }

    //  특정 ID의 게시글을 조회
    @GetMapping("/{postId}")
    @Operation(summary = "게시글 상세 조회")
    public ResponseEntity<Optional<Post>> getPostById(@PathVariable Long postId) { // 경로 매개변수를 통해 postId 수신
        Optional<Post> post = postService.getPostById(postId); // PostService를 통해 게시글과 관련된 모든 데이터 조회

        if (post.isEmpty()) { // 게시글이 존재하지 않을 경우 404 Not Found 응답
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(post); // 게시글이 존재할 경우 200 OK와 함께 게시글 데이터 응답
    }

    // 새로운 게시글을 생성
    @PostMapping
    @Operation(summary = "새로운 게시글 생성")
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
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
