package world.startoy.polling.adapter.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import world.startoy.polling.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByPostUid(String postUid);

    List<Post> findAll();
}
