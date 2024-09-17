package com.startoy.pollhub.adapter.controller;

import com.startoy.pollhub.adapter.repository.PostRepository;
import com.startoy.pollhub.domain.Post;
import com.startoy.pollhub.usecase.PostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Log4j2
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;


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


    // 특정 ID의 게시글을 수정
    @PutMapping("/{postId}")
    @Operation(summary = "특정 게시글 수정")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @Valid @RequestBody Post post) {
        try {

            Post updatedPost = postService.updatePost(postId, post);
            return ResponseEntity.ok(updatedPost);

        } catch (RuntimeException e) {
            log.error("게시물 수정 중 오류 발생: {}",e.getMessage());
            return ResponseEntity.notFound().build();
        }

    }


    // 특정 ID의 게시글을 삭제
    @DeleteMapping("/{postId}")
    @Operation(summary = "게시글 삭제")
    ResponseEntity<String> deletePost(@PathVariable Long postId) {
        try {
            postService.deletePost(postId);
            return ResponseEntity.ok().build();  // 삭제 성공 시 200 OK 반환

        } catch (EntityNotFoundException e) {

            return ResponseEntity.notFound().build();  // 게시글이 없는 경우 404 Not Found 반환
        } catch (Exception e) {

            return ResponseEntity.notFound().build();
        }
    }
}
