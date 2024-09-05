package com.startoy.pollhub.repository;

import com.startoy.pollhub.adapter.repository.PostRepository;
import com.startoy.pollhub.domain.Post;
import com.startoy.pollhub.usecase.PostService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Log4j2
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    //

////    @Test
////    public void testCreatePost() {
////        // Given : 테스트용 post 객체 생성
////        Post post = Post.builder()
////                .title("테스트")
////                .fileId(1L)
////                .isDeleted(false)
////                .createdBy("테스트")
////                .build();
////
////        // When : PostService를 이용해 Post 생성
////        Post savedPost = postService.createPost(post);
////
////        // Then: 생성된 Post가 실제로 저장되었는지 확인
////        Optional<Post> foundPost = postRepository.findById(savedPost.getId());
////        assertThat(foundPost).isPresent();
////        assertThat(foundPost.get().getTitle()).isEqualTo("테스트 제목");
////        assertThat(foundPost.get().getCreatedBy()).isEqualTo("테스터");
////    }
//
    @Test
    public void testSave() {
        LongStream.rangeClosed(1L,20L).forEach(i -> {
            Post post = Post.builder()
                    .title("title" + i)
                    .fileId(i)
                    .pollId(1L)
                    .isDeleted(false)
                    .createdBy("createdBy" + i)
                    .build();
            Post result = postRepository.save(post);  //JPA는 자동으로 만들어주기 때문에 내가 만들지 않은 save 메소드도 나온다.
            log.info(result);
        });
    }
//
    @Test
    public void testSelect() {
        Long pid = 14L;
        Optional<Post> result = postRepository.findById(pid);
        assertThat(result).isPresent(); // result가 비어 있지 않고 실제 값을 포함하고 있는지 확인
        assertThat(result.get().getTitle()).isEqualTo("title2");
    }

    @Test
    public void testDelete() {
        Long id = 8L;

        Optional<Post> result = postRepository.findById(id);
        Post post = result.orElseThrow();

        assertThat(result).isPresent();
        postRepository.delete(post);
    }

}
