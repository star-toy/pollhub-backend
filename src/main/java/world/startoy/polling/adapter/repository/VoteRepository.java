package world.startoy.polling.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.startoy.polling.domain.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
}
