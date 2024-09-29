package world.startoy.polling.adapter.repository;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import world.startoy.polling.domain.Vote;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    // 특정 투표에 특정 ip 사용자 투표 정보 조회
    List<Vote> findByPollIdAndVoterIp(Long pollId, String voterIp);

    List<Vote> findByPollId(Long pollId);

    // 투표 옵션 및 득표수 가져오는 쿼리
    @Query("SELECT " +
            "o.pollOptionUid as pollOptionUid, " +
            "o.pollOptionText as pollOptionText, " +
            "o.pollOptionSeq as pollOptionSeq, " +
            "f.fileUid as fileUid, " +
            "f.fileFullName as fileFullName, " +
            "COUNT(v.id) as votedCount " +
            "FROM Vote v " +
            "JOIN PollOption o ON v.optionId = o.id " +
            "LEFT JOIN FileStorage f ON o.file.id = f.id " +  // FileStorage와 JOIN
            "WHERE v.pollId = :pollId " +
            "GROUP BY o.pollOptionUid, o.pollOptionText, o.pollOptionSeq, f.fileUid, f.fileFullName")
    List<Tuple> countVotesByPollId(@Param("pollId") Long pollId);


}

