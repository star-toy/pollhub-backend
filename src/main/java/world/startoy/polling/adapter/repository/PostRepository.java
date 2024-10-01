package world.startoy.polling.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import world.startoy.polling.domain.Post;
import world.startoy.polling.usecase.dto.PostDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByPostUid(String postUid);

    List<Post> findAll();

    @Query("SELECT new world.startoy.polling.usecase.dto.PostDTO(p.postUid, p.title, f.fileUid, f.fileName, p.createdBy, p.createdAt) " +
            "FROM Post p LEFT JOIN p.file f")
    List<PostDTO> findAllPostWithFile();
}
