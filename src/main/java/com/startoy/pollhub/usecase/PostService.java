package com.startoy.pollhub.usecase;

import com.startoy.pollhub.domain.PostPost;
import com.startoy.pollhub.adapter.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository PostRepository;

    public PostService(PostRepository PostRepository) {
        this.PostRepository = PostRepository;
    }

    public List<PostPost> getAllPosts() {
        return PostRepository.findAll();
    }

    public Optional<PostPost> getPostById(Long id) {
        return PostRepository.findById(id);
    }

    public PostPost createPost(PostPost post) {
        return PostRepository.save(post);
    }

    public PostPost updatePost(Long id, PostPost post) {
        Optional<PostPost> existingPost = PostRepository.findById(id);
        if (existingPost.isPresent()) {
            PostPost toUpdate = existingPost.get();
            toUpdate.setTitle(post.getTitle());
            toUpdate.setContent(post.getContent());
            return PostRepository.save(toUpdate);
        }
        return null;
    }

    public void deletePost(Long id) {
        PostRepository.deleteById(id);
    }
}
