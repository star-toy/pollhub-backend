package com.startoy.pollhub.adapter.controller;

import com.startoy.pollhub.domain.Post;
import com.startoy.pollhub.usecase.PostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 모든 게시글을 조회
    @GetMapping
    @Operation(summary = "모든 게시글 조회")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    //  특정 ID의 게시글을 조회
    @GetMapping("/{id}")
    @Operation(summary = "게시글 조회")
    public Optional<Post> getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
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
