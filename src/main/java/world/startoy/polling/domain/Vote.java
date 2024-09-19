package world.startoy.polling.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pl_vote")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id", nullable = false)
    private Long voteId;

    @Column(name = "vote_uid", unique = true, nullable = false, length = 36)
    private String voteUid;

    @ManyToOne
    @JoinColumn(name = "poll_id", nullable = false)
    private Poll poll;

    @ManyToOne
    @JoinColumn(name = "option_id", nullable = false)
    private PollOption pollOption;

    @Column(name = "voter_ip", nullable = false, length = 255)
    private String voterIp;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
