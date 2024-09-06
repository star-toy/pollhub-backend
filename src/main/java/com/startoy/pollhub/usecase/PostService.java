package com.startoy.pollhub.usecase;

import com.startoy.pollhub.domain.Poll;
import com.startoy.pollhub.domain.Post;
import com.startoy.pollhub.adapter.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PostService {

    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final PollService pollService;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long postId) {
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
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    // 특정 ID의 게시글을 업데이트
    public Post updatePost(Long id, Post post) {
        Optional<Post> existingPost = postRepository.findById(id);
        if (existingPost.isPresent()) {
            Post toUpdate = existingPost.get();
            toUpdate.setTitle(post.getTitle());
            return postRepository.save(toUpdate);
        }
        return null;
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
